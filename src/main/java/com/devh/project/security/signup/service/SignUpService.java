package com.devh.project.security.signup.service;

import com.devh.project.common.entity.RedisUser;
import com.devh.project.common.entity.User;
import com.devh.project.common.helper.AES256Helper;
import com.devh.project.common.helper.AuthKeyHelper;
import com.devh.project.common.helper.BCryptHelper;
import com.devh.project.common.helper.MailHelper;
import com.devh.project.common.repository.RedisUserRepository;
import com.devh.project.common.repository.UserRepository;
import com.devh.project.security.signup.SignUpStatus;
import com.devh.project.security.signup.dto.SignUpRequestDTO;
import com.devh.project.security.signup.dto.SignUpResponseDTO;
import com.devh.project.security.signup.exception.DuplicateEmailException;
import com.devh.project.security.signup.exception.PasswordException;
import com.devh.project.security.signup.exception.SignUpException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SignUpService {

    private final AES256Helper aes256Helper;
    private final UserRepository userRepository;
    private final RedisUserRepository redisUserRepository;
    private final AuthKeyHelper authKeyHelper;
    private final BCryptHelper bcryptHelper;
    private final MailHelper mailService;

    public SignUpResponseDTO signUpByMemberSignUpRequestVO(SignUpRequestDTO signUpRequestDTO) {
    	final String username = signUpRequestDTO.getUsername();
    	/* exist check */
    	if(userRepository.existsByUsername(username))
    		throw new DuplicateEmailException(username+" already exists.");
    	/* save temporary until email authentication */
    	RedisUser redisUser = redisUserRepository.save(toRedisUser(signUpRequestDTO));
    	/* send mail */
    	mailService.sendSignupValidationMail(signUpRequestDTO.getUsername(), redisUser.getAuthKey());
    	/* return sign up response */
    	return SignUpResponseDTO.builder()
    			.signUpStatus(StringUtils.equals(username, redisUser.getUsername()) ? SignUpStatus.REQUESTED : SignUpStatus.ERROR)
    			.username(username)
    			.build();
    }
    
    public SignUpResponseDTO commitSignUpByEmailAndAuthKey(String username, String authKey) {
		/* redis check */
		RedisUser redisUser = redisUserRepository.findById(username).orElse(null);
		if(redisUser == null)
			throw new SignUpException("Failed to sign up ["+username+"]. Maybe time expired.");
		/* auth key check */
		if(!StringUtils.equals(authKey, redisUser.getAuthKey()))
			throw new SignUpException("Invalid Authentication URL.");
		/* db check */
		if(userRepository.existsByUsername(username))
			throw new SignUpException("Already exists.");
		/* save */
		User user = userRepository.save(toUser(redisUser));
		redisUserRepository.deleteById(username);
		return SignUpResponseDTO.builder()
				.signUpStatus(StringUtils.equals(username, user.getUsername()) ? SignUpStatus.COMPLETED : SignUpStatus.ERROR)
				.username(username)
				.build();
    }

    private User toUser(RedisUser redisMember) {
        return (User) User.builder()
                .username(redisMember.getUsername())
                .password(redisMember.getPassword())
                .build();
    }
    
    private RedisUser toRedisUser(SignUpRequestDTO signUpRequestDTO) throws PasswordException {
    	try {
    		return RedisUser.builder()
    				.username(signUpRequestDTO.getUsername())
    				.password(bcryptHelper.encode(aes256Helper.decrypt(signUpRequestDTO.getPassword())))
    				.authKey(authKeyHelper.generateAuthKey())
    				.build();
    	} catch (Exception e) {
			throw new PasswordException("Something wrong with your password. "+e.getMessage());
		}
    }
    
}

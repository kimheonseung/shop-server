package com.devh.project.common.service;

import com.devh.project.common.entity.RedisMember;
import com.devh.project.common.exception.DuplicateEmailException;
import com.devh.project.common.exception.PasswordException;
import com.devh.project.common.exception.SignUpException;
import com.devh.project.common.constant.SignUpStatus;
import com.devh.project.common.dto.SignUpRequestDTO;
import com.devh.project.common.dto.SignUpResponseDTO;
import com.devh.project.common.entity.Member;
import com.devh.project.common.helper.AES256Helper;
import com.devh.project.common.helper.AuthKeyHelper;
import com.devh.project.common.helper.BCryptHelper;
import com.devh.project.common.helper.MailHelper;
import com.devh.project.common.repository.RedisMemberRepository;
import com.devh.project.common.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SignUpService {

    private final AES256Helper aes256Helper;
    private final MemberRepository memberRepository;
    private final RedisMemberRepository redisMemberRepository;
    private final AuthKeyHelper authKeyHelper;
    private final BCryptHelper bcryptHelper;
    private final MailHelper mailService;

    public SignUpResponseDTO signUpByMemberSignUpRequestVO(SignUpRequestDTO signUpRequestDTO) {
    	final String username = signUpRequestDTO.getUsername();
    	/* exist check */
    	if(memberRepository.existsByUsername(username))
    		throw new DuplicateEmailException(username+" already exists.");
    	/* save temporary until email authentication */
    	RedisMember redisMember = redisMemberRepository.save(toRedisMember(signUpRequestDTO));
    	/* send mail */
    	mailService.sendSignupValidationMail(signUpRequestDTO.getUsername(), redisMember.getAuthKey());
    	/* return sign up response */
    	return SignUpResponseDTO.builder()
    			.signUpStatus(StringUtils.equals(username, redisMember.getUsername()) ? SignUpStatus.REQUESTED : SignUpStatus.ERROR)
    			.username(username)
    			.build();
    }
    
    public SignUpResponseDTO commitSignUpByEmailAndAuthKey(String username, String authKey) {
		/* redis check */
		RedisMember redisMember = redisMemberRepository.findById(username).orElse(null);
		if(redisMember == null)
			throw new SignUpException("Failed to sign up ["+username+"]. Maybe time expired.");
		/* auth key check */
		if(!StringUtils.equals(authKey, redisMember.getAuthKey()))
			throw new SignUpException("Invalid Authentication URL.");
		/* db check */
		if(memberRepository.existsByUsername(username))
			throw new SignUpException("Already exists.");
		/* save */
		Member member = memberRepository.save(toMember(redisMember));
		redisMemberRepository.deleteById(username);
		return SignUpResponseDTO.builder()
				.signUpStatus(StringUtils.equals(username, member.getUsername()) ? SignUpStatus.COMPLETED : SignUpStatus.ERROR)
				.username(username)
				.build();
    }

    private Member toMember(RedisMember redisMember) {
        return (Member) Member.builder()
                .username(redisMember.getUsername())
                .password(redisMember.getPassword())
                .build();
    }
    
    private RedisMember toRedisMember(SignUpRequestDTO signUpRequestDTO) throws PasswordException {
    	try {
    		return RedisMember.builder()
    				.username(signUpRequestDTO.getUsername())
    				.password(bcryptHelper.encode(aes256Helper.decrypt(signUpRequestDTO.getPassword())))
    				.authKey(authKeyHelper.generateAuthKey())
    				.build();
    	} catch (Exception e) {
			throw new PasswordException("Something wrong with your password. "+e.getMessage());
		}
    }
    
}

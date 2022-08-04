package com.devh.project.security.signup.controller;

import com.devh.project.common.constant.ApiStatus;
import com.devh.project.common.dto.ApiResponseDTO;
import com.devh.project.security.signup.dto.SignUpRequestDTO;
import com.devh.project.security.signup.dto.SignUpResponseDTO;
import com.devh.project.security.signup.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignUpController {

    private final SignUpService signUpService;

    @Value("${aes.key}")
	private String key;

    @GetMapping
	public ModelAndView getSignUp() {
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("/signup.html");
    	mav.addObject("aesKey", key);
    	return mav;
	}

    @PostMapping
    public ApiResponseDTO<SignUpResponseDTO> signUp(@Valid @RequestBody SignUpRequestDTO signUpRequestDTO) throws Exception {
        return ApiResponseDTO.success(ApiStatus.Success.OK, signUpService.signUpByMemberSignUpRequestVO(signUpRequestDTO));
    }
    
    @GetMapping("/complete")
    public ModelAndView signUpComplete(@RequestParam(name = "email") String email, @RequestParam(name = "authKey") String authKey) throws Exception {
		SignUpResponseDTO signUpResponseDTO = signUpService.commitSignUpByEmailAndAuthKey(email, authKey);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/signup-complete.html");
    	mav.addObject("message", signUpResponseDTO.getSignUpStatus().toString());
    	return mav;
    }
}

package com.devh.project.common.constant;

import lombok.Getter;

/**
 * <pre>
 * Description :
 *     API 결과 상태 관련 상수를 갖는 클래스
 * ===============================================
 * Member fields :
 *     Success
 *     ClientError
 *     ServerError
 *     CustomError
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2022. 7. 14.
 * </pre>
 */
public class ApiStatus {
    @Getter
    public enum Success {
        OK("Ok", 200, "Standard response for successful HTTP requests.");

        private final String status;
        private final int code;
        private final String description;

        Success(String status, int code, String description) {
            this.status = status;
            this.code = code;
            this.description = description;
        }
    }
    
    @Getter
    public enum AuthError {
    	UNAUTHORIZED("Unauthorized", 401, "Unauthoriazed."),
    	ACCESS_DENIED("Access Denied", 403, "Access denied.");
    	
    	private final String status;
    	private final int code;
    	private final String description;
    	
    	AuthError(String status, int code, String description) {
    		this.status = status;
    		this.code = code;
    		this.description = description;
    	}
    }

    @Getter
    public enum ServerError {
        INTERNAL_SERVER_ERROR("Internal Server Error", 500, "Unexpected condition was encountered.");

        private final String status;
        private final int code;
        private final String description;

        ServerError(String status, int code, String description) {
            this.status = status;
            this.code = code;
            this.description = description;
        }
    }

    @Getter
    public enum CustomError {
        VALIDATION_ERROR("Validation Error", 800, "Invalid value."),
        DUPLICATE_EMAIL_ERROR("Duplicate Email Error", 801, "email duplicated"),
        PASSWORD_ERROR("Password Error", 801, "Something wrong with password value."),
        SIGNUP_ERROR("SignUp Error", 801, "Sign up failed."),
        TOKEN_ERROR("Token Error", 802, "Something wrong with your token."),
        TOKEN_GENERATE_ERROR("Token Generate Error", 802, "Token generate failed."),
        TOKEN_INVALIDATE_ERROR("Token Invalidate Error", 802, "Token invalidate failed."),
        TOKEN_REFRESH_ERROR("Token Refresh Error", 802, "Token refresh failed."),
        ;

        private final String status;
        private final int code;
        private final String description;

        CustomError(String status, int code, String description) {
            this.status = status;
            this.code = code;
            this.description = description;
        }
    }
}
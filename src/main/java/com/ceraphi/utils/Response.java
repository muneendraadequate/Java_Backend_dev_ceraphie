package com.ceraphi.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class Response {
    public Response(String token,Long id,String username){
        this.token=token;
        this.id=id;
        this.username=username;
    }
    private String token;
    private Long id;
    private String username;

}

package com.ceraphi.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.batik.util.io.StringNormalizingReader;

@Data
@NoArgsConstructor
@Builder
public class Response {
    public Response(String token,Long id,String username,String role){
        this.token=token;
        this.id=id;
        this.username=username;
        this.role=role;
    }
    private String token;
    private Long id;
    private String username;
    private String role;

}

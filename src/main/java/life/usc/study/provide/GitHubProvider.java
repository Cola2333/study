package life.usc.study.provide;

import com.alibaba.fastjson.JSON;
import life.usc.study.dto.AccessTokenDTO;
import life.usc.study.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GitHubProvider {
    /*
     * Exchange this code for an access token: with parameters saved in accessTokenDTO
     * */
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
//            System.out.println(s);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

/*
* Use the access token to access the API
* */
    public GitHubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String string =  response.body().string();
            GitHubUser gitHubUser = JSON.parseObject(string, GitHubUser.class); //将json格式的数据转换成GitHubUser格式 会自动匹配驼峰
            return gitHubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

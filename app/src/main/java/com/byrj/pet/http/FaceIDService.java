package com.byrj.pet.http;

import com.byrj.pet.http.response.BaseResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * 接口
 *
 * @author xll
 * @date 2018/12/4
 */

public interface FaceIDService {

    /**
     * 登录
     *
     * @param url
     * @param map account pwd
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<BaseResponse<Map<String, Object>>> login(@Url String url, @FieldMap Map<String, Object> map);

    /**
     * 发送验证码
     *
     * @param url
     * @param map account pwd
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<BaseResponse<Map<String, Object>>> sendLoginCode(@Url String url, @FieldMap Map<String, Object> map);

}


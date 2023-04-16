/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package tour.base.utils;

import java.io.Serializable;


public class RedisKeys {

    public static String getSysConfigKey(String key){
        return "tour: sys: config: " + key;
    }

    /**
     * TODO 邮箱验证码 key
     */
    public static String getEmailKey(String email) {
        return "tour: email: " + email;
    }

    /**
     * TODO 用户的关注列表 key
     */
    public static String getUserFollowKey(Serializable userId) {
        return "tour: user: " + userId + ": follow";
    }

    /**
     * TODO 用户的粉丝列表 key
     */
    public static String getUserFanKey(Serializable userId) {
        return "tour: user: " + userId + ": fan";
    }

    /**
     * TODO 用户的访客数 key
     */
    public static String getUserVisitCntKey(Serializable userId) {
        return "tour: user: " + userId + ": visitCnt";
    }

    /**
     * TODO 用户的喜欢数 key
     */
    public static String getUserLoveCntKey(Serializable userId) {
        return "tour: user: " + userId + ": loveCnt";
    }

    /**
     * TODO 点赞这篇文章的用户 key
     */
    public static String getArticleLoveKey(Long articleId) {
        return "tour: article: " + articleId + ": love";
    }

    /**
     * TODO 点赞评论的用户 key
     */
    public static String getCommentLoveKey(Long commentId) {
        return "tour: comment: " + commentId + ": love";
    }
    //评论数
    public static String getArticleCommentKey(Long articleId,String type){
        return "tour: type: " + type + ";article:" + articleId + "comment";
    }

}

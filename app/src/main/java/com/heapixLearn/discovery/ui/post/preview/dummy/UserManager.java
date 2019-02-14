package com.heapixLearn.discovery.ui.post.preview.dummy;

import com.heapixLearn.discovery.ui.post.preview.IUserManager;

import java.util.ArrayList;
import java.util.List;

public class UserManager implements IUserManager {
    List<User> users = new ArrayList<>();

    @Override
    public User getUserByAccountId(long accountId) {
        init();
        for(User user : users){
            if(user.getId() == accountId){
                return user;
            }
        }
        return null;
    }

    public void init(){
        users.add(new User(1, "Viachaslau", "https://pp.userapi.com/c852228/v852228571/7181b/p1f3T3uQ1B8.jpg",
                120, true, true));
        users.add(new User(2, "Vitaliy", "http://skachat-kartinki.ru/img/picture/Nov/16/fa46483b4e2866d866625ff4d40468ed/mini_1.jpg",
                20, false, true));
        users.add(new User(3, "Ilon Mask", "https://news.liga.net/images/general/2019/02/12/thumbnail-tw-20190212101950-5918.jpg",
                10000, false, false));
    }
}

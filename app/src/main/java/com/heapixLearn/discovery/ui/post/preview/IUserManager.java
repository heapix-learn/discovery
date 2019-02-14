package com.heapixLearn.discovery.ui.post.preview;

import com.heapixLearn.discovery.ui.post.preview.entity.IUser;

public interface IUserManager {
    IUser getUserByAccountId(long accountId);
}

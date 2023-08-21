package com.statuswa.fasttalkchat.toolsdownload.interfaces;


import com.statuswa.fasttalkchat.toolsdownload.model.FBStoryModel.NodeModel;
import com.statuswa.fasttalkchat.toolsdownload.model.story.TrayModel;

public interface UserListInterface {
    void userListClick(int position, TrayModel trayModel);
    void fbUserListClick(int position, NodeModel trayModel);
}

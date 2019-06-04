package com.learn.ankitkumar.Util;

import android.support.v7.util.DiffUtil;

import com.learn.ankitkumar.Model.MessageModel;

import java.util.List;

public class MyDiffUtilCallback extends DiffUtil.Callback {
    private List<MessageModel> oldList;
    private List<MessageModel> newList;

    public MyDiffUtilCallback(List<MessageModel> oldList, List<MessageModel> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldPosition, int newPosition) {
        return oldList.get(oldPosition).getSender().equals(newList.get(newPosition).getSender());
    }

    @Override
    public boolean areContentsTheSame(int oldPosition, int newPosition) {
        return oldList.get(oldPosition).getText().equals(newList.get(newPosition).getText());
    }

}

package com.xiaojiu.qq.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaojiu.qq.R;
import com.xiaojiu.qq.utils.StringUtils;

import java.util.List;

/**
 * 作者：${xiaojiukeji} on 17/1/15 09:50
 * 作用:
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> implements IContactAdapter {
    private List<String> contactList;

    public ContactAdapter(List<String> contactList) {
        this.contactList = contactList;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_contact, parent, false);
        ContactViewHolder contactViewHolder = new ContactViewHolder(view);
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, final int position) {
        final String contact = contactList.get(position);
        holder.tv_userName.setText(contact);
        String initial = StringUtils.getInitial(contact);
        holder.tv_section.setText(initial);
        if (position == 0) {
            holder.tv_section.setVisibility(View.VISIBLE);
        } else {
            //获取上一个首字母
            String preContact = contactList.get(position - 1);
            String preInitial1 = StringUtils.getInitial(preContact);
            if (preInitial1.equals(initial)) {
                holder.tv_section.setVisibility(View.GONE);
            } else {
                holder.tv_section.setVisibility(View.VISIBLE);
            }
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (onItemLongClickListener != null){
                    onItemLongClickListener.onItemLongClick(contact,position);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList == null ? 0 : contactList.size();
    }

    @Override
    public List<String> getData() {
        return contactList;
    }


    class ContactViewHolder extends RecyclerView.ViewHolder{
        TextView tv_section, tv_userName;

        public ContactViewHolder(View itemView) {
            super(itemView);
            tv_section = (TextView) itemView.findViewById(R.id.tv_section);
            tv_userName = (TextView) itemView.findViewById(R.id.tv_username);
        }
    }

    public OnItemLongClickListener onItemLongClickListener;

    public interface OnItemLongClickListener {
        void onItemLongClick(String contact, int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }
}

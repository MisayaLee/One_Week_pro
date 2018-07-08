package com.example.misaya.one_week_pro.One;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.misaya.one_week_pro.R;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {
    private ArrayList<ContactEntity> mContacts = new ArrayList<ContactEntity>();

    private static final String[] PHONES_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID};

    /**
     * 联系人显示名称
     **/
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;

    /**
     * 电话号码
     **/
    private static final int PHONES_NUMBER_INDEX = 1;

    /**
     * 头像ID
     **/
    private static final int PHONES_PHOTO_ID_INDEX = 2;

    /**
     * 联系人的ID
     **/
    private static final int PHONES_CONTACT_ID_INDEX = 3;
    private RecyclerView rv_id;
    private String name ;
    private String phone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        initView(view);

        getPhoneContacts();

        rv_id.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        rv_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getActivity().getIntent();
                name = intent.getStringExtra("name");
                phone = intent.getStringExtra("phone");
                // 获取SharedPreferences对象
                SharedPreferences sp = getActivity().getSharedPreferences("FILENAME", Activity.MODE_PRIVATE);
                //获取SP编辑器
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("name", name);
                edit.putString("pass", phone);
                //提交edit
                edit.commit();
                Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_LONG).show();
            }
        });

        MyRvAdapter rvAdapter = new MyRvAdapter(getActivity(), mContacts);
        rv_id.setAdapter(rvAdapter);

        return view;
    }

    private void getPhoneContacts() {
        ContentResolver resolver = getActivity().getContentResolver();
        try {
            // 获取手机联系人
            Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    PHONES_PROJECTION, null, null, null);
            if (phoneCursor != null) {
                while (phoneCursor.moveToNext()) {

                    // 得到手机号码
                    String phoneNumber = phoneCursor
                            .getString(PHONES_NUMBER_INDEX);
                    // 当手机号码为空的或者为空字段 跳过当前循环
                    if (TextUtils.isEmpty(phoneNumber))
                        continue;
                    System.out.println("======================");
                    System.out.println("---手机号码："+phoneNumber);


                    // 得到联系人名称
                    String contactName = phoneCursor
                            .getString(PHONES_DISPLAY_NAME_INDEX);
                    System.out.println("---联系人："+contactName);

                    // 得到联系人ID
                    Long contactid = phoneCursor
                            .getLong(PHONES_CONTACT_ID_INDEX);
                    System.out.println("---联系人id："+contactid);

                    // 得到联系人头像ID
                    Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);
                    System.out.println("---联系人头像："+photoid);

                    // 得到联系人头像Bitamp
                    Bitmap contactPhoto = null;

                    // photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
                    if (photoid > 0) {
                        Uri uri = ContentUris.withAppendedId(
                                ContactsContract.Contacts.CONTENT_URI,
                                contactid);
                        InputStream input = ContactsContract.Contacts
                                .openContactPhotoInputStream(resolver, uri);
                        contactPhoto = BitmapFactory.decodeStream(input);
                        System.err.println("---联系人有头像："+photoid);
                    } else {
                        contactPhoto = BitmapFactory.decodeResource(
                                getResources(), R.mipmap.ic_launcher);
                    }
                    ContactEntity mContact = new ContactEntity(contactName,
                            phoneNumber, contactPhoto);
                    mContacts.add(mContact);
                }
                phoneCursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView(View view) {
        rv_id = (RecyclerView) view.findViewById(R.id.rv_id);
    }
}

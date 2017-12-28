package com.ecs.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecs.pojo.ContactDetail;
import com.ecs.runtimepermissions.R;
import com.ecs.runtimepermissions.SampleActivity;

import java.util.List;

/**
 * Created by Manish on 28-12-2017.
 */

public class ContactAdapter extends BaseAdapter {
    private SampleActivity activity;
    private List<ContactDetail> arrayList;

    public ContactAdapter(SampleActivity sampleActivity, List<ContactDetail> list) {
        activity = sampleActivity;
        arrayList = list;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        /*View rootView = View.inflate(activity, R.layout.list_row, null);
        ContactDetail contactDetail=arrayList.get(i);
        Holder holder = new Holder();
        holder.contactName = (TextView) rootView.findViewById(R.id.tv_contactName);
        holder.contactNumber = (TextView) rootView.findViewById(R.id.tv_contactNumber);
        holder.imgContactPicture = (ImageView) rootView.findViewById(R.id.imgContactPicture);*/
        ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.list_row, null);
            holder = new ViewHolder();
            holder.contactName = convertView.findViewById(R.id.tv_contactName);
            holder.contactNumber = convertView.findViewById(R.id.tv_contactNumber);
            holder.imgContactPicture = convertView.findViewById(R.id.imgContactPicture);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ContactDetail contactDetail = arrayList.get(i);

        holder.contactName.setText(contactDetail.getContactName());
        holder.contactNumber.setText(contactDetail.getContactNumber());
        //holder.personImageView.setImageBitmap(person.getImage());

        boolean flag = setContactImageIfAvailable(holder, contactDetail.getContactName(), contactDetail.getContactNumber());
        if (!flag) {
            int r = (int) (Math.random() * 256);
            int g = (int) (Math.random() * 256);
            int b = (int) (Math.random() * 256);
            holder.imgContactPicture.setImageBitmap(generateCircleBitmap(activity, Color.rgb(r, g, b), 50.0f, "" + contactDetail.getContactName().charAt(0)));
        }

        return convertView;
    }

    public class ViewHolder {
        TextView contactName, contactNumber;
        ImageView imgContactPicture;
    }

    public boolean setContactImageIfAvailable(ViewHolder mHolder, String name, String no) {
        boolean foundFlag = false;
        Cursor cur = activity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String contactName = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String phoneNumber = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String number = phoneNumber.replaceAll("\\s", "");
                if (contactName.equalsIgnoreCase(name) && number.equalsIgnoreCase(no)) {
                    String contactImageUri = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                    if (contactImageUri != null) {
                        mHolder.imgContactPicture.setImageURI(Uri.parse(contactImageUri));
                        BitmapDrawable drawable = (BitmapDrawable) mHolder.imgContactPicture.getDrawable();
                        Bitmap bitmap = drawable.getBitmap();
                        Bitmap op = getRoundedShape(bitmap);
                        mHolder.imgContactPicture.setImageBitmap(op);
                        foundFlag = true;
                    }
                }
            }
        }
        cur.close();
        return foundFlag;
    }

    public static Bitmap generateCircleBitmap(Context context, int circleColor, float diameterDP, String text) {
        final int textColor = 0xffffffff;
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();

        diameterDP = 30.0f;

        float diameterPixels = diameterDP * (metrics.densityDpi / 160f);
        float radiusPixels = diameterPixels / 2;
        // Create the bitmap
        Bitmap output = Bitmap.createBitmap((int) diameterPixels, (int) diameterPixels, Bitmap.Config.ARGB_8888);
        // Create the canvas to draw on
        Canvas canvas = new Canvas(output);
        canvas.drawARGB(0, 0, 0, 0);
        // Draw the circle
        final Paint paintC = new Paint();
        paintC.setAntiAlias(true);
        paintC.setColor(circleColor);
        canvas.drawCircle(radiusPixels, radiusPixels, radiusPixels, paintC);
        // Draw the text
        if (text != null && text.length() > 0) {
            final Paint paintT = new Paint();
            paintT.setColor(textColor);
            paintT.setAntiAlias(true);
            paintT.setTextSize(radiusPixels * 2);
            final Rect textBounds = new Rect();
            paintT.getTextBounds(text, 0, text.length(), textBounds);
            canvas.drawText(text, radiusPixels - textBounds.exactCenterX(), radiusPixels - textBounds.exactCenterY(), paintT);
        }
        return output;
    }

    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float diameterDP = 0.0f;

        diameterDP = 30.0f;


        float diameterPixels = diameterDP * (metrics.densityDpi / 160f);
        int targetWidth = (int) diameterPixels;
        int targetHeight = (int) diameterPixels;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2, ((float) targetHeight - 1) / 2, (Math.min(((float) targetWidth),
                ((float) targetHeight)) / 2), Path.Direction.CCW);
        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap, new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth, targetHeight), null);
        return targetBitmap;
    }


}

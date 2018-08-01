package com.solarecoding.demos.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class ImageViewerDialogFragment extends DialogFragment {
    public static final String EXTRA_DATE = "com.solarecoding.demos.criminalintent.image";
    private static final String ARG_IMAGE_UUID = "image_uuid";

    public static ImageViewerDialogFragment newInstance(UUID crimeId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_IMAGE_UUID, crimeId);

        ImageViewerDialogFragment fragment = new ImageViewerDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_IMAGE_UUID);
        Crime mCrime = CrimeLab.getCrimeLab(getActivity()).getCrime(crimeId);
        File mPhotoFile = CrimeLab.getCrimeLab(getActivity()).getPhotoFile(mCrime);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_image_viewer, null);
        ImageView mPhotoView = view.findViewById(R.id.imageView_imageViewer);
        // 在View中显示图片
        if(mPhotoFile == null || !mPhotoFile.exists()){
            mPhotoView.setImageDrawable(null);
        } else{
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.crime_show_image_detail)
                .create();
    }


}

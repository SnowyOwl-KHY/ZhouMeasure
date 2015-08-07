package classexample.zhoumeasure.reference;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import classexample.zhoumeasure.MainActivity;
import classexample.zhoumeasure.R;

/**
 * Created by Xiaozhou on 2015/8/7.
 */
public class ReferenceFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reference, container, false);
    }

    private static final float APPEAR_ALPHA = 1.0f;
    private static final float DISAPPEAR_ALPHA = 0.0f;
    private static final String DATA_KEY = "referenceObjectListDataKey";
    private static final String SEPARATOR = "@@";

    private Button mBtnStartButton;
    private Button mBtnEndButton;

    private ImageView mIvCameraImage;
    private ImageView mIvBackImage;

    private View mReferenceLayout;
    private ListView mLvReference;
    private ScrollView mSvContentList;

    private double mSelectedLength = 0;

    private ReferenceListAdapter mReferenceListAdapter;

    @Override
    public void onStart() {
        super.onStart();
        initialUIElement();
    }

    private void initialUIElement() {

        mBtnStartButton = (Button) getActivity().findViewById(R.id.btnStartPosition);
        mBtnEndButton = (Button) getActivity().findViewById(R.id.btnEndPosition);

        mIvCameraImage = (ImageView) getActivity().findViewById(R.id.ivCameraImage);
        mIvCameraImage.setOnClickListener(onClickListener);
        mIvBackImage = (ImageView) getActivity().findViewById(R.id.ivBackImage);
        mIvBackImage.setOnClickListener(onClickListener);

        mReferenceLayout = getActivity().findViewById(R.id.referenceLayout);

        mLvReference = (ListView) getActivity().findViewById(R.id.lvReference);
        mSvContentList = (ScrollView) getActivity().findViewById(R.id.svContentList);

        simplyInitialListView();

        initialDetail();

    }

    View oldDetail;
    View newDetail;

    private void initialDetail() {
        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        newDetail = inflater.inflate(R.layout.reference_detail, null);
        ((TableLayout) getActivity().findViewById(R.id.detailContainer)).addView(newDetail);

        TextView tvName = (TextView) newDetail.findViewById(R.id.tvReferenceName);
        TextView tvLength = (TextView) newDetail.findViewById(R.id.tvReferenceSize);
        TextView tvDescription = (TextView) newDetail.findViewById(R.id.tvReferenceDescription);

        tvName.setText("name");
        tvLength.setText("Length:\t\t" + " cm");
        tvDescription.setText("This is a description about the one whose length is known.");
    }

    private void simplyInitialListView() {
        ArrayList<HashMap<String, String>> listData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", "Known length" + i);
            map.put("length", "Length: " + (i * 10) + "cm");
            listData.add(map);
        }

        mReferenceListAdapter = new ReferenceListAdapter();
        mLvReference.setAdapter(mReferenceListAdapter);
        mLvReference.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (oldDetail != null) {
                    ((TableLayout) oldDetail.getParent()).removeView(oldDetail);
                }
                oldDetail = newDetail;

                LayoutInflater inflater = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                newDetail = inflater.inflate(R.layout.reference_detail, null);
                ((TableLayout) getActivity().findViewById(R.id.detailContainer)).addView(newDetail);
                newDetail.setY(oldDetail.getY());
                newDetail.setX(oldDetail.getX() + oldDetail.getWidth());

                TextView tvName = (TextView) newDetail.findViewById(R.id.tvReferenceName);
                TextView tvLength = (TextView) newDetail.findViewById(R.id.tvReferenceSize);
                TextView tvDescription = (TextView) newDetail.findViewById(R.id.tvReferenceDescription);
                ImageView ivImage = (ImageView) newDetail.findViewById(R.id.ivReferenceImage);
                tvName.setText(mReferenceListAdapter.objects.get(position).name);
                tvLength.setText("Length:\t\t" + mReferenceListAdapter.objects.get(position).length + " cm");
                tvDescription.setText(mReferenceListAdapter.objects.get(position).description);
                ivImage.setImageResource(R.drawable.ruler);
                mSelectedLength = mReferenceListAdapter.objects.get(position).length;

                newDetail.animate().setInterpolator(new AccelerateDecelerateInterpolator())
                        .x(oldDetail.getX()).y(oldDetail.getY()).setDuration(Utils.ANIMATION_TIME);
                oldDetail.animate().setInterpolator(new AccelerateDecelerateInterpolator())
                        .x(oldDetail.getX() - oldDetail.getWidth()).y(oldDetail.getY()).setDuration(Utils.ANIMATION_TIME);
            }
        });
    }

    public void addReferenceObject(ReferenceObject referenceObject) {
        SharedPreferences sp = getActivity().getSharedPreferences("SP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String strObjects = sp.getString(DATA_KEY, null);
        if (strObjects != null) {
            strObjects += SEPARATOR;
        } else {
            strObjects = "";
        }
        strObjects += referenceObject.toString();
        editor.putString(DATA_KEY, strObjects);
        editor.commit();
        mReferenceListAdapter.update();
    }

    class ReferenceListAdapter extends BaseAdapter {

        ArrayList<View> itemViews;
        ArrayList<ReferenceObject> objects = new ArrayList<>();

        public ReferenceListAdapter() {
            itemViews = new ArrayList();
            update();
        }

        public void update() {
            objects.clear();
            itemViews.clear();
            SharedPreferences sp = ReferenceFragment.this.getActivity().getSharedPreferences("SP", Context.MODE_PRIVATE);
            String temp = sp.getString(DATA_KEY, null);
            if (temp != null && !temp.equals("")) {
                String[] strObjects = temp.split(SEPARATOR);
                for (String strObject : strObjects) {
                    addReferenceObject(new ReferenceObject(strObject));
                }
            }
        }

        public void addReferenceObject(ReferenceObject referenceObject) {
            objects.add(referenceObject);
            itemViews.add(makeView(referenceObject));
            notifyDataSetChanged();
        }

        private View makeView(ReferenceObject object) {
            LayoutInflater inflater = (LayoutInflater) ReferenceFragment.this.getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.listitem_reference, null);

            TextView tvName = (TextView) itemView.findViewById(R.id.tvReferenceName);
            tvName.setText(object.name);
            TextView tvLength = (TextView) itemView.findViewById(R.id.tvReferenceSize);
            tvLength.setText("Length:\t\t" + object.length + " cm");
            ImageView ivImage = (ImageView) itemView.findViewById(R.id.ivReferenceImage);
            ivImage.setImageResource(R.drawable.ruler);
            return itemView;
        }

        @Override
        public int getCount() {
            return itemViews.size();
        }

        @Override
        public Object getItem(int position) {
            return itemViews.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return itemViews.get(position);
        }
    }

    static class ReferenceObject {
        String name;
        float length;
        String description;

        public ReferenceObject(String name, float length, String description) {
            this.name = name;
            this.length = length;
            this.description = description;
        }

        public ReferenceObject(String string) {
            String[] temp = string.split(",");
            this.name = temp[0];
            this.length = Float.valueOf(temp[1]);
            this.description = temp[2];
        }

        @Override
        public String toString() {
            return name + "," + length + "," + description;
        }
    }

    public View.OnClickListener onClickListener = new View.OnClickListener() {

        private boolean fragmentIsVisible = true;
        private int attachedFragment = R.layout.fragment_camera;

        @Override
        public void onClick(View view) {

            float barGap = mReferenceLayout.getHeight();
            float bottomGap = mSvContentList.getHeight();

            switch (view.getId()) {
                case R.id.ivCameraImage:
                    if (fragmentIsVisible) {
                        fragmentIsVisible = false;
                        Utils.moveTo(view, mBtnEndButton.getX(), mBtnEndButton.getY());
                        Utils.slideUp(mReferenceLayout, barGap);
                        Utils.slideUp(mSvContentList, -bottomGap);
                        Utils.changeAlpha(mIvBackImage, APPEAR_ALPHA);
                        ((MainActivity) getActivity()).getPhotoFragment().setRefLength(mSelectedLength);
                    } else {
                        switch (attachedFragment) {
                            case R.layout.fragment_camera:
                                attachedFragment = R.layout.fragment_photo;
                                ((MainActivity) getActivity()).getCameraFragment().takePhoto();
                                ((MainActivity) getActivity()).jumpToPhotoFragment();
                                break;
                            case R.layout.fragment_photo:
                                attachedFragment = R.layout.fragment_camera;
                                ((MainActivity) getActivity()).jumpToCameraFragment();
                                ((MainActivity) getActivity()).getPhotoFragment().setRefLength(mSelectedLength);
                                break;
                        }
                    }
                    break;
                case R.id.ivBackImage:
                    fragmentIsVisible = true;
                    Utils.moveTo(mIvCameraImage, mBtnStartButton.getX(), mBtnStartButton.getY());
                    Utils.slideDown(mReferenceLayout);
                    Utils.slideDown(mSvContentList);
                    Utils.changeAlpha(mIvBackImage, DISAPPEAR_ALPHA);
                    break;
            }
        }
    };
}

class Utils {

    static final int ANIMATION_TIME = 700;

    public static void slideUp(View view, float delta) {
        view.animate().setInterpolator(new AccelerateDecelerateInterpolator())
                .translationY(-delta)
                .setDuration(ANIMATION_TIME);
    }

    public static void slideDown(View view) {
        view.animate().setInterpolator(new AccelerateDecelerateInterpolator())
                .translationY(0)
                .setDuration(ANIMATION_TIME);
    }

    public static void moveTo(View view, float aimX, float aimY) {
        view.animate().setInterpolator(new AccelerateDecelerateInterpolator())
                .x(aimX)
                .y(aimY)
                .setDuration(ANIMATION_TIME);
    }

    public static void changeAlpha(View view, float aimAlpha) {
        view.animate().setInterpolator(new AccelerateDecelerateInterpolator())
                .alpha(aimAlpha)
                .setDuration(ANIMATION_TIME);
    }
}


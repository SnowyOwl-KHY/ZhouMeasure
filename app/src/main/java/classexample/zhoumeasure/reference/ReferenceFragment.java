package classexample.zhoumeasure.reference;

import android.app.Fragment;
import android.content.Context;
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
    static final float APPEAR_ALPHA = 1.0f;
    static final float DISAPPEAR_ALPHA = 0.0f;

    private Button mBtnStartButton;
    private Button mBtnEndButton;

    private ImageView mIvCameraImage;
    private ImageView mIvBackImage;

    private View mReferenceLayout;

    private ListView mLvReference;
    private ScrollView mSvContentList;

    @Override
    public void onStart() {
        super.onStart();
        initialUIElement();
    }

    private void initialUIElement() {

        mBtnStartButton = (Button) getActivity().findViewById(R.id.btnStartPosition);
        mBtnEndButton = (Button) getActivity().findViewById(R.id.btnEndPosition);

        mIvCameraImage = (ImageView) getActivity().findViewById(R.id.ivCameraImage);
        mIvBackImage = (ImageView) getActivity().findViewById(R.id.ivBackImage);
        mIvCameraImage.setOnClickListener(cameraImageClickListener);
        mIvBackImage.setOnClickListener(cameraImageClickListener);

        mReferenceLayout = getActivity().findViewById(R.id.knownLengthLayout);

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
        TextView tvDate = (TextView) newDetail.findViewById(R.id.tvReferenceDate);
        TextView tvDescription = (TextView) newDetail.findViewById(R.id.tvReferenceDescription);
//        ImageView ivImage = (ImageView) newDetail.findViewById(R.id.ivReferenceImage);

        tvName.setText("name");
        tvLength.setText("Length:\t\t" + " cm");
        tvDate.setText("Date: ");
        tvDescription.setText("This is a description about the one whose length is known.");
//        ivImage.setImageResource(R.drawable.camera_icon);
    }

    private void simplyInitialListView() {
        ArrayList<HashMap<String, String>> listData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", "Known length" + i);
            map.put("length", "Length: " + (i * 10) + "cm");
            listData.add(map);
        }

        mLvReference.setAdapter(new ReferenceListAdapter());
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
                TextView tvDate = (TextView) newDetail.findViewById(R.id.tvReferenceDate);
                TextView tvDescription = (TextView) newDetail.findViewById(R.id.tvReferenceDescription);
                ImageView ivImage = (ImageView) newDetail.findViewById(R.id.ivReferenceImage);

                tvName.setText(objects[position].name);
                tvLength.setText("Length:\t\t" + objects[position].length + " cm");
                tvDate.setText("Date: " + objects[position].date);
                tvDescription.setText(objects[position].description);
                ivImage.setImageResource(objects[position].imageSrcId);

                newDetail.animate().setInterpolator(new AccelerateDecelerateInterpolator())
                        .x(oldDetail.getX()).y(oldDetail.getY()).setDuration(Utils.ANIMATION_TIME);
                oldDetail.animate().setInterpolator(new AccelerateDecelerateInterpolator())
                        .x(oldDetail.getX()-oldDetail.getWidth()).y(oldDetail.getY()).setDuration(Utils.ANIMATION_TIME);
            }
        });
    }

    static ReferenceObject[] objects = new ReferenceObject[7];
    static String[] names = {"ruler", "finger", "hand", "phone", "laptop", "pad", "pen"};
    static float[] lengths = {20.0f, 8.0f, 15.0f, 12.0f, 25.0f, 18.0f, 15.0f};
    static int[] imageId = {R.drawable.ruler, R.drawable.finger, R.drawable.hand, R.drawable.phone, R.drawable.laptop,
            R.drawable.pad, R.drawable.pen};

    static {
        for (int i = 0; i < objects.length; i++) {
            objects[i] = new ReferenceObject(names[i], lengths[i], "2015-08-0" + (i + 1),
                    "This is a description about the one whose length is known.", imageId[i]);
        }
    }

    class ReferenceListAdapter extends BaseAdapter {

        View[] itemViews;

        public ReferenceListAdapter() {
            itemViews = new View[objects.length];
            for (int i = 0; i < itemViews.length; i++) {
                itemViews[i] = makeView(objects[i]);
            }
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
            ivImage.setImageResource(object.imageSrcId);
            return itemView;
        }

        @Override
        public int getCount() {
            return itemViews.length;
        }

        @Override
        public Object getItem(int position) {
            return itemViews[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView != null) {
                return convertView;
            }
            return itemViews[position];
        }
    }

    static class ReferenceObject {
        String name;
        float length;
        String date;
        String description;
        int imageSrcId;

        public ReferenceObject(String name, float length, String date, String description, int imageSrcId) {
            this.name = name;
            this.length = length;
            this.date = date;
            this.description = description;
            this.imageSrcId = imageSrcId;
        }
    }

    View.OnClickListener cameraImageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            float barGap = mReferenceLayout.getHeight();
            float bottomGap = mSvContentList.getHeight();

            if (Utils.getCenterY(view) == Utils.getCenterY(mBtnStartButton)) { // Move down

                // FAB animation

                Utils.moveTo(view, mBtnEndButton, APPEAR_ALPHA);
                Utils.moveTo(mIvCameraImage, mBtnEndButton, DISAPPEAR_ALPHA);
                Utils.moveTo(mIvBackImage, mBtnEndButton, APPEAR_ALPHA);

                // Fat Bar animation
                Utils.slideUp(mReferenceLayout, barGap);

                // Content
                Utils.slideUp(mSvContentList, -bottomGap);

            } else if (Utils.getCenterY(view) == Utils.getCenterY(mBtnEndButton)) { //Move up

                // FAB animation

                Utils.moveTo(view, mBtnStartButton, APPEAR_ALPHA);
                Utils.moveTo(mIvCameraImage, mBtnStartButton, APPEAR_ALPHA);
                Utils.moveTo(mIvBackImage, mBtnStartButton, DISAPPEAR_ALPHA);

                // Fat Bar animation
                Utils.slideDown(mReferenceLayout);

                //Content
                Utils.slideDown(mSvContentList);

            }
        }
    };
}

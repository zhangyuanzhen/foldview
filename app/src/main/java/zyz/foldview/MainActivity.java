package zyz.foldview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String string = "哈哈哈哈哈哈哈哈哈哈哈哈哈和哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈和哈哈哈哈哈哈" +
            "哈哈哈哈哈哈哈哈哈和哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈和哈哈" +
            "哈哈哈哈哈哈哈哈哈哈哈哈哈和哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈";
    private int minLines = 2;//默认显示的行数
    private TextView textview;
    private ImageView imageView;
    private boolean isExpand;
    private int durationMillis = 350;//动画持续时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageview);
        imageView.setOnClickListener(this);
        textview = (TextView) findViewById(R.id.textview);
        textview.setText(string);
        textview.setHeight(textview.getLineHeight() * minLines);
        textview.post(new Runnable() {
            @Override
            public void run() {
                imageView.setVisibility(textview.getLineCount() > minLines ? View.VISIBLE : View.GONE);
            }
        });

    }


    @Override
    public void onClick(View v) {
        isExpand = !isExpand;
        textview.clearAnimation();
        final int maxHeight;//目标高度
        final int minHeight = textview.getHeight();//起始高度
        int durationMillis = 350;//动画持续时间
        if (isExpand) {
            /**
             * 折叠动画
             * 从实际高度缩回起始高度
             */
            maxHeight = textview.getLineHeight() * textview.getLineCount() - minHeight;
            RotateAnimation animation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(durationMillis);
            animation.setFillAfter(true);
            imageView.startAnimation(animation);
        } else {
            /**
             * 展开动画
             * 从起始高度增长至实际高度
             */
            maxHeight = textview.getLineHeight() * minLines - minHeight;
            RotateAnimation animation = new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(durationMillis);
            animation.setFillAfter(true);
            imageView.startAnimation(animation);
        }
        Animation animation = new Animation() {
            protected void applyTransformation(float interpolatedTime, Transformation t) { //根据ImageView旋转动画的百分比来显示textview高度，达到动画效果
                textview.setHeight((int) (minHeight + maxHeight * interpolatedTime));
            }
        };
        animation.setDuration(durationMillis);
        textview.startAnimation(animation);
    }

}

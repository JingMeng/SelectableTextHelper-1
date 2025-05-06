package com.jaeger.selectabletexthelper;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.jaeger.library.OnSelectListener;
import com.jaeger.library.SelectableTextHelper;

import io.noties.markwon.AbstractMarkwonPlugin;
import io.noties.markwon.LinkResolverDef;
import io.noties.markwon.Markwon;
import io.noties.markwon.MarkwonConfiguration;
import io.noties.markwon.html.HtmlPlugin;

/**
 * 1. 点击空白消失(主要就是可以被消失补充一下)
 *
 * https://github.com/itsCoder/weeklyblog
 *
 */
public class MainActivity extends AppCompatActivity {

    private TextView mTvTest;

    private SelectableTextHelper mSelectableTextHelper;
    private LinearLayout llRoot;
    private TextView tvTest;

    Markwon markwon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        markwon = Markwon.builder(this)
                // required plugin to support inline parsing
                .usePlugin(HtmlPlugin.create())
                .usePlugin(new AbstractMarkwonPlugin() {
                    @Override
                    public void configureConfiguration(@NonNull MarkwonConfiguration.Builder builder) {
                        builder.linkResolver(new LinkResolverDef()); // 启用默认链接处理器
                    }
                })
                .build();

        mTvTest = (TextView) findViewById(R.id.tv_test);

        mSelectableTextHelper = new SelectableTextHelper.Builder(mTvTest)
                .setSelectedColor(getResources().getColor(R.color.selected_blue))
                .setCursorHandleSizeInDp(20)
                .setCursorHandleColor(getResources().getColor(R.color.cursor_handle_color))
                .build();

        mSelectableTextHelper.setSelectListener(new OnSelectListener() {
            @Override
            public void onTextSelected(CharSequence content) {
                //这个地方无法提示已选择那些文本，但是可以设置键盘消失灯事件，这个是重复执行的
                Toast.makeText(MainActivity.this, content, Toast.LENGTH_SHORT).show();
            }
        });

        mTvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "点击事件被触发了", Toast.LENGTH_SHORT).show();
            }
        });


        markwon.setMarkdown(mTvTest, "欢迎访问 [OpenAI 官网](https://www.openai.com) 了解更多信息。\n" +
                "\n" +
                "你也可以查看 [GitHub 上的 ChatGPT 项目](https://github.com/openai) 获取最新的开源代码。\n" +
                "\n" +
                "如果你对 Markdown 语法不熟悉，可以阅读 [Markdown 官方文档](https://daringfireball.net/projects/markdown/)。\n");


    }


    @Override
    protected void onDestroy() {
        mSelectableTextHelper.destroy();
        super.onDestroy();
    }
}

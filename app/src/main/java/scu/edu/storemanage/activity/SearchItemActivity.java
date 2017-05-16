package scu.edu.storemanage.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.zxing.activity.CaptureActivity;

import java.util.ArrayList;

import scu.edu.storemanage.R;
import scu.edu.storemanage.database.ItemDatabase;
import scu.edu.storemanage.item.Item;
import scu.edu.storemanage.tools.ItemChoseAdapter;

/**
 * Created by 周秦春 on 2017/4/11.
 */

public class SearchItemActivity extends Activity {

    //该用户下的数据库
    private SQLiteDatabase database;
    //商品数据库
    private ItemDatabase itemDatabase;
    //商品适配器
    private ItemChoseAdapter itemChoseAdapter;

    //UI
    private ImageButton return_button;
    private ImageButton scan_button;
    private ListView item_list;
    private EditText search_edit;
    private Button search_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search_item_layout);
        initUIComponent();//初始化控件

        //初始化数据库的名字
        database = MainFunctionActivity.getDatabase();
        if (database == null) {
            Toast.makeText(this, "读取数据库失败", Toast.LENGTH_SHORT).show();
            finish();
        }
        //获得商品数据库
        itemDatabase = new ItemDatabase(database);

        //监听返回按钮
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //监听扫描条形码
        //监听跳转到扫描二条形码界面
        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent barcodeIntent = new Intent(SearchItemActivity.this, CaptureActivity.class);
                startActivityForResult(barcodeIntent, 2048);
            }
        });

        //监听搜索按钮
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //监听查询按钮
                String search_info;
                search_info = search_edit.getText().toString();

                //如果输入内容为空
                if (search_info.equals("")) {
                    return;
                }

                //如果输入内容不为空
                //尝试通过ID查找商品
                final ArrayList<Item> allItems = new ArrayList<Item>();
                int ID;
                try{
                    ID = Integer.parseInt(search_info);
                    Item item = itemDatabase.searchByItemID(ID);
                    allItems.add(item);
                }catch (Exception e){

                }
                //尝试通过名称查找
                ArrayList<Item> allItemsByName = itemDatabase.searchByNameLike(search_info);
                if (allItemsByName != null) {
                    allItems.addAll(allItemsByName);
                }

                //显示查找到的信息
                if (!allItems.isEmpty()){
                    //信息
                    itemChoseAdapter = new ItemChoseAdapter(SearchItemActivity.this, R.layout.member_listview_item_layout, allItems);
                    item_list.setAdapter(itemChoseAdapter);

                    //设置Item点击监听
                    item_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //传入Item对象
                            ItemChangeActivity.setItem(allItems.get(position));
                            //传入ItemDatabase对象
                            ItemChangeActivity.setItemDatabase(itemDatabase);
                            //启动Activity
                            Intent itemIntent = new Intent(SearchItemActivity.this, ItemChangeActivity.class);
                            startActivity(itemIntent);
                        }
                    });
                }else{
                    Toast.makeText(SearchItemActivity.this, "没有查找到此商品", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * 初始化UI控件
     */
    private void initUIComponent() {
        return_button = (ImageButton) findViewById(R.id.return_button_in_search_layout);
        scan_button = (ImageButton) findViewById(R.id.scan_button_in_search_layout);
        item_list = (ListView) findViewById(R.id.item_listview);
        search_edit = (EditText) findViewById(R.id.search_edit_in_search_layout);
        search_button = (Button) findViewById(R.id.search_button_in_search_layout);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 2048:
                if (resultCode == RESULT_OK) {
                    //获得条形码
                    String barcode = data.getExtras().getString("result");

                    //判断数据库中是否曾经拥有该商品
                    final ArrayList<Item> items = itemDatabase.searchByBarcode(barcode);
                    if (items != null) {

                        itemChoseAdapter = new ItemChoseAdapter(this, R.layout.member_listview_item_layout, items);
                        item_list.setAdapter(itemChoseAdapter);

                        //设置Item点击监听
                        item_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //传入Item对象
                                ItemChangeActivity.setItem(items.get(position));
                                //传入ItemDatabase对象
                                ItemChangeActivity.setItemDatabase(itemDatabase);
                                //启动Activity
                                Intent itemIntent = new Intent(SearchItemActivity.this, ItemChangeActivity.class);
                                startActivity(itemIntent);
                            }
                        });

                    } else {
                        Toast.makeText(this, "商品不存在", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(this, "扫描条码失败", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }

        return;
    }
}

package ruan.com.retrofit2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import ruan.com.Net.HttpManager.HttpResponse;
import ruan.com.Net.HttpManager.Interface.HttpCallback;
import ruan.com.Net.MqttManager.Interface.MqttCallback;
import ruan.com.Net.MqttManager.MqttRequest;
import ruan.com.Net.UdpManager.Interface.UdpCallback;
import ruan.com.Net.UdpManager.UdpRequest;
import ruan.com.Utils.AppApplicationMgr;
import ruan.com.Utils.AppPhoneMgr;
import ruan.com.View.BaseRecycleView.BaseAdapterResp;
import ruan.com.View.OnItemClickListener;
import ruan.com.retrofit2.BaseControl.BaseActivity;
import ruan.com.retrofit2.BaseControl.CrashFactory;
import ruan.com.retrofit2.Http.RequestApi;
import ruan.com.retrofit2.Language.OnChangeLanguageEvent;
import ruan.com.retrofit2.Log.LogFactory;
import ruan.com.View.BaseRecycleView.BaseRecyclerViewAdapter;

/**
 * Created by 19820 on 2018/1/4.
 */

public class MainActivity extends BaseActivity implements IView , HttpCallback.Response , UdpCallback.Response , MqttCallback.Response{


    public Context context;

    //当一个成员变量被@Inject注解修饰，并且它的类型构造函数也被@Inject注解修饰,dagger2就会自动实例化该成员类型，并注入到该成员变量
    @Inject
    TestPresent mPresent;

    private HttpCallback.Response response;

    private MqttRequest mqttRequest;

    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * activity  start
     */
    @Override
    protected void init() {
        context = this;
        setTitleVisiable(false);

        response = this;

        EventBus.getDefault().register(this);

        setDoubleQuit(true);


        addContentView(R.layout.activity_main);

//        mqttRequest = new MqttRequest(context, new MqttCallback.Response() {
//            /**
//             * 请求成功
//             *
//             * @param topic 请求标识
//             * @param message     请求返回的数据
//             */
//            @Override
//            public void onSuccess(String topic, String message) {
//                Toast.makeText(context , message , Toast.LENGTH_SHORT).show();
//            }
//
//            /**
//             * 请求失败
//             *
//             * @param topic 请求标识
//             * @param throwable   请求失败的对象
//             */
//            @Override
//            public void onError(String topic, Throwable throwable) {
//                Toast.makeText(context , throwable.toString() , Toast.LENGTH_SHORT).show();
//            }
//        });
//        mqttRequest.init("456");
        final UdpRequest request = new UdpRequest(this , this);
        request.init();

        findViewById(R.id.textview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = "hello word!!";
                request.send(msg.getBytes(Charset.forName("utf-8")) , 5506 , "192.168.101.21" , 200);

//                //将数据上传到服务器
//                BugUploadBean bugUploadBean = new BugUploadBean();
//                AppPhoneMgr appPhoneMgr = new AppPhoneMgr();
//                bugUploadBean.setModel(appPhoneMgr.getPhoneModel());
//                bugUploadBean.setDebugOSVersion(AppPhoneMgr.getSystemVersion() + " " + appPhoneMgr.getSDKVersionNumber());
//                if (appPhoneMgr.isTablet(context))
//                    bugUploadBean.setPhoneType("pad");
//                bugUploadBean.setAppPackage(AppApplicationMgr.getPackageName(context));
//                bugUploadBean.setAppInstallDate(formatter.format(new Date(AppApplicationMgr.getAppFirstInstallTime(context , bugUploadBean.getAppPackage()))));
//                bugUploadBean.setAppInstallUpdateDate(formatter.format(new Date(AppApplicationMgr.getAppLastUpdateTime(context , bugUploadBean.getAppPackage()))));
//                bugUploadBean.setAppVersionName(AppApplicationMgr.getVersionName(context));
//                bugUploadBean.setAppVersionCode(AppApplicationMgr.getVersionCode(context));
//                bugUploadBean.setBugData("test bug");
//                bugUploadBean.setBround(AppPhoneMgr.getDeviceBrand());
//                bugUploadBean.setDebugOS(AppPhoneMgr.getDeviceSystem());

//                new RequestApi(context).UploadBug(bugUploadBean, new HttpCallback.Response() {
//
//                    @Override
//                    public void onSuccess(int requestCode, HttpResponse response) {
//                        HttpBaseResp resp = (HttpBaseResp) response;
//                        LogFactory.getInstance(CrashFactory.class).i("UploadBug" , resp.toString());
//                        if (resp.getCode() == 200000){
//                            System.out.println("bug 提交成功");
//                        }
//                    }
//
//                    @Override
//                    public void onError(int requestCode, Throwable throwable) {
//
//                    }
//                } , 200);

//                List<String> list = null;
//                System.out.println(list.toString());
//                mqttRequest.send("123" , "hello mqtt client , I m mqtt other client");

//                Intent intent = new Intent(MainActivity.this , TestActivity.class);
//                startActivity(intent);
            }
        });

//        TwinklingRefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
//        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
//            @Override
//            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
//                super.onRefresh(refreshLayout);
////                refreshLayout.finishRefreshing();
//            }
//
//            @Override
//            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
//                super.onLoadMore(refreshLayout);
//                refreshLayout.finishLoadmore();
//            }
//        });

//        RecyclerView recyclerView = findViewById(R.id.recyclerView);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(OrientationHelper.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
//
//        ArrayList<BaseAdapterResp> arrayList = new ArrayList<>();
//        for (int i = 0 ; i < 100 ; i++) {
//            ItemData itemData = new ItemData();
//            itemData.setItem_t("hello！！");
//            arrayList.add(itemData);
//        }
//        BaseRecyclerViewAdapter adapter = new BaseRecyclerViewAdapter(this , R.layout.item_main , 1 , arrayList);
//        adapter.setOnItemClickLisneter(new OnItemClickListener() {
//
//            /**
//             * 列表点击
//             *
//             * @param resp     返回对象
//             * @param Type     类型
//             * @param position 点击列表标识
//             */
//            @Override
//            public void OnItemClick(BaseAdapterResp resp, int Type, int position) {
////                ItemData itemData = (ItemData) resp;
////                System.out.println(itemData.toString());
//                new RequestApi(MainActivity.this).RequestTopic(response , 1);
//            }
//        });
//        recyclerView.setAdapter(adapter);
//        recyclerView.addItemDecoration(new DividerItemDecoration(this , LinearLayoutManager.VERTICAL));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeLanguageEvent(OnChangeLanguageEvent event){
        Toast.makeText(context , "切换成功" , Toast.LENGTH_SHORT).show();
        LogFactory.getInstance(MainActivity.class).i("onChangeLanguageEvent" , "ChangeLanguage");
    }

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        RecyclerView recyclerView = findViewById(R.id.recyclerView);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(OrientationHelper.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
//
//        ArrayList<BaseAdapterResp> arrayList = new ArrayList<>();
//        ItemData itemData = new ItemData();
//        itemData.setItem_t("hello！！");
//        arrayList.add(itemData);
//        BaseRecyclerViewAdapter adapter = new BaseRecyclerViewAdapter(this , R.layout.item_main , 1 , arrayList);
//
//        recyclerView.setAdapter(adapter);
//
//        context = this;

//        final MqttRequest mqttRequest = new MqttRequest(context , this);
//        mqttRequest.init("test");

//        findViewById(R.id.textview).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mqttRequest.send("test" , "测试 android send data");
//            }
//        });


//        EventBus.getDefault().register(this);

//        findViewById(R.id.textview).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this , TestActivity.class);
//                startActivity(intent);
//            }
//        });


//        String msg = "你好 udp server";

//        new UdpRquest(context , this).request(msg.getBytes(Charset.forName("utf-8")) , "192.168.1.101" , 5066 , 1);

//        MqttManager mqttManager = new MqttManager(this);
//        mqttManager.



//        DaggerTestComponent.builder().githubApiModule(new GithubApiModule(this)).testModule(new TestModule(this)).build().inject(this);//@Component负责连接起@Inject和@Module注解
//        mPresent.updateUI();

//        Retrofit retrofit = new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .baseUrl("http://39.108.192.191:8080/fanghuaLaw/")
//                .build();

//        final RegisterRequest registerRequest = new RegisterRequest();
//        registerRequest.setPage(1);
//        registerRequest.setType(0);

//        UserService apiService = retrofit.create(UserService.class);
//        Observable<?> observable = apiService.gettopicpage(1 , 0);
//        Observable<BaseObject> objectObservable = observable;

//        new HttpRequest(context , this).request(observable , 1);


//        observable.subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread())
//                .subscribe(
//                        new Observer<BaseObject>() {
//                            @Override
//                            public void onCompleted() {
//
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                System.out.println(e.toString());
//                            }
//
//                            @Override
//                            public void onNext(BaseObject baseObject) {
//                                EncodeData encodeData = (EncodeData) baseObject;
//                                System.out.println(encodeData.toString());
//                            }
//                        }
//
//                );


//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }


    @Subscribe
    public void onMessage(ListBean bean){
        Toast.makeText(this , bean.getAddress() , Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onMessage(String bean){
        Toast.makeText(this , "string:" + bean , Toast.LENGTH_SHORT).show();
    }


    @Override
    public void updateUI(String text) {
        ((TextView) findViewById(R.id.textview)).setText(text);
    }

    /**
     * 请求成功
     *
     * @param requestCode 请求标识
     * @param response  请求返回的对象
     */
    @Override
    public void onSuccess(int requestCode, HttpResponse response) {
        EncodeData encodeData = (EncodeData) response;
//        System.out.println("success:" + encodeData.toString());
        Toast.makeText(this , encodeData.getMessage() , Toast.LENGTH_SHORT).show();
    }

    /**
     * 请求失败
     *
     * @param requestCode 请求标识
     * @param throwable   请求失败的对象
     */
    @Override
    public void onError(int requestCode, Throwable throwable) {
        LogFactory.getInstance(MainActivity.class).i("onError" , throwable.toString());
    }

    /**
     * 请求成功
     *
     * @param requestCode 请求标识
     * @param msg         请求返回的数据
     */
    @Override
    public void onUdpSuccess(@Nullable Integer requestCode, @Nullable String msg) {
        Toast.makeText(context , msg , Toast.LENGTH_SHORT).show();
    }

    /**
     * 请求失败
     *
     * @param requestCode 请求标识
     * @param throwable   请求失败的对象
     */
    @Override
    public void onUdpError(@Nullable Integer requestCode, @Nullable Throwable throwable , @Nullable Integer code) {
        Toast.makeText(context , throwable.toString() , Toast.LENGTH_SHORT).show();
    }

    /**
     * 请求成功
     *
     * @param requestCode 请求标识
     * @param message     请求返回的数据
     */
    @Override
    public void onSuccess(String requestCode, String message) {
        Toast.makeText(context , requestCode + "--" + message , Toast.LENGTH_SHORT).show();
    }

    /**
     * 请求失败
     *
     * @param requestCode 请求标识
     * @param throwable   请求失败的对象
     */
    @Override
    public void onError(String requestCode, Throwable throwable) {
        Toast.makeText(context , requestCode + "--" + throwable.toString() , Toast.LENGTH_SHORT).show();
    }

}

/**
 * 定义一个View层的统一接口
 * View接口保持不变
 */
interface IView {

    void updateUI(String text);

}


/**
 * Present类，调用Model层的业务方法，更新View层的界面展示
 */
class TestPresent {
    IView mView;
    @Inject
    TestModel mModel;//Dagger2遇到@Inject标记的成员属性，就会去查看该成员类的构造函数，如果构造函数也被@Inject标记,则会自动初始化，完成依赖注入。

    //TestPresent的构造函数也被@Inject注解修饰
    @Inject
    public TestPresent(IView view) {
        this.mView = view;
    }

    public void updateUI() {
        mView.updateUI(mModel.getText());
    }
}

/**
 * Model类，实现具体的业务逻辑
 */
class TestModel {
    //构造函数用@Inject修饰
    @Inject
    public TestModel() {
    }

    public String getText() {
        return "Dagger2应用实践...";
    }
}

/**
 * Module类提供那些没有构造函数的类的依赖，如第三方类库，系统类，接口类
 */
@Module
class TestModule {
    private IView mView;

    public TestModule(IView iView) {
        this.mView = iView;
    }

    //@Provides注解的方法，提供IView类的依赖。
    @Provides
    public IView provideIView() {
        return this.mView;
    }
}


/**
 * Module类提供那些没有构造函数的类的依赖，如第三方类库，系统类，接口类
 */
@Module
class GithubApiModule {

    private IView mView;

    public GithubApiModule(IView iView) {
        this.mView = iView;
    }
    //@Provides注解的方法，提供IView类的依赖。
//    @Provides
//    public IView provideIView(){
//        return this.mView;
//    }
}


/**
 * Component必须是一个接口类或者抽象
 */
@Component(modules = {TestModule.class, GithubApiModule.class})
interface TestComponent {
    void inject(MainActivity mainActivity);
}

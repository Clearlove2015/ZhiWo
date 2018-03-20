package com.zhaoqy.self.ui.activity.main.tool.calculator;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.activity.main.tool.calculator.InputItem.InputType;
import com.zhaoqy.self.ui.base.BaseBarActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CalculatorActivity extends BaseBarActivity implements View.OnClickListener {

    public static final int INPUT_NUMBER = 1;
    public static final int INPUT_POINT = 0;
    public static final int INPUT_OPERATOR = -1;
    public static final int END = -2;
    public static final int ERROR= -3;
    public static final int SHOW_RESULT_DATA = 1;
    public static final String nan = "NaN";
    public static final String infinite = "∞";

    @BindView(R.id.show_result_tv)
    TextView mShowResultTv;
    @BindView(R.id.show_input_tv)
    TextView mShowInputTv;
    @BindView(R.id.c_btn)
    Button mCBtn;
    @BindView(R.id.del_btn)
    Button mDelBtn;
    @BindView(R.id.add_btn)
    Button mAddBtn;
    @BindView(R.id.sub_btn)
    Button mSubBtn;
    @BindView(R.id.multiply_btn)
    Button mMultiplyBtn;
    @BindView(R.id.divide_btn)
    Button mDividebtn;
    @BindView(R.id.zero_btn)
    Button mZeroButton;
    @BindView(R.id.one_btn)
    Button mOnebtn;
    @BindView(R.id.two_btn)
    Button mTwoBtn;
    @BindView(R.id.three_btn)
    Button mThreeBtn;
    @BindView(R.id.four_btn)
    Button mFourBtn;
    @BindView(R.id.five_btn)
     Button mFiveBtn;
    @BindView(R.id.six_btn)
    Button mSixBtn;
    @BindView(R.id.seven_btn)
    Button mSevenBtn;
    @BindView(R.id.eight_btn)
    Button mEightBtn;
    @BindView(R.id.nine_btn)
    Button mNineBtn;
    @BindView(R.id.point_btn)
    Button mPointtn;
    @BindView(R.id.equal_btn)
    Button mEqualBtn;
    @BindView(R.id.percent)
    Button mpercent;

    private HashMap<View,String> map; //将View和String映射起来
    private List<InputItem> mInputList; //定义记录每次输入的数
    private int mLastInputstatus = INPUT_NUMBER; //记录上一次输入状态

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_calculator;
    }

    @Override
    protected void initData() {
        if(map == null)
            map = new HashMap<View, String>();
        map.put(mAddBtn,getResources().getString(R.string.calculator_add));
        map.put(mMultiplyBtn,getResources().getString(R.string.calculator_multply));
        map.put(mDividebtn,getResources().getString(R.string.calculator_divide));
        map.put(mSubBtn, getResources().getString(R.string.calculator_sub));
        map.put(mZeroButton ,getResources().getString(R.string.calculator_zero));
        map.put(mOnebtn,getResources().getString(R.string.calculator_one));
        map.put(mTwoBtn,getResources().getString(R.string.calculator_two));
        map.put(mThreeBtn,getResources().getString(R.string.calculator_three));
        map.put(mFourBtn,getResources().getString(R.string.calculator_four));
        map.put(mFiveBtn,getResources().getString(R.string.calculator_five));
        map.put(mSixBtn,getResources().getString(R.string.calculator_six));
        map.put(mSevenBtn,getResources().getString(R.string.calculator_seven));
        map.put(mEightBtn,getResources().getString(R.string.calculator_eight));
        map.put(mNineBtn,getResources().getString(R.string.calculator_nine));
        map.put(mPointtn,getResources().getString(R.string.calculator_point));
        map.put(mEqualBtn,getResources().getString(R.string.calculator_equal));
        map.put(mpercent,getResources().getString(R.string.calculator_percent));
        mInputList = new ArrayList<InputItem>();
        mShowResultTv.setText("");
        clearAllScreen();
    }

    @OnClick({R.id.c_btn, R.id.del_btn, R.id.point_btn, R.id.equal_btn,  R.id.add_btn, R.id.sub_btn, R.id.multiply_btn,
            R.id.divide_btn, R.id.percent, R.id.one_btn, R.id.two_btn, R.id.three_btn, R.id.four_btn, R.id.five_btn,
            R.id.six_btn, R.id.seven_btn, R.id.eight_btn, R.id.nine_btn})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.c_btn:
                clearAllScreen();
                break;
            case R.id.del_btn:
                back();
                break;
            case R.id.point_btn:
                inputPoint(view);
                break;
            case R.id.equal_btn:
                operator();
                break;
            case R.id.add_btn:
            case R.id.sub_btn:
            case R.id.multiply_btn:
            case R.id.divide_btn:
            case R.id.percent:
                inputOperator(view);
                break;
            default:
                inputNumber(view);
                break;
        }
    }

    /**
     * 点击=之后开始运算
     */
    private void operator() {
        if(mLastInputstatus == END ||mLastInputstatus == ERROR || mLastInputstatus == INPUT_OPERATOR|| mInputList.size()==1){
            return;
        }
        mShowResultTv.setText("");
        startAnim();
        findHighOperator(0);
        if(mLastInputstatus != ERROR){
            findLowOperator(0);
        }
        mHandler.sendMessageDelayed(mHandler.obtainMessage(SHOW_RESULT_DATA), 300);
    }

    private void startAnim(){
        mShowInputTv.setText(mShowInputTv.getText()+getResources().getString(R.string.calculator_equal));
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.screen_anim);
        mShowInputTv.startAnimation(anim);
    }
    /**
     * 输入点
     * @param view
     */
    private void inputPoint(View view) {
        if(mLastInputstatus == INPUT_POINT){
            return;
        }
        if(mLastInputstatus == END || mLastInputstatus == ERROR){
            clearInputScreen();
        }
        String key = map.get(view);
        String input = mShowInputTv.getText().toString();
        if(mLastInputstatus == INPUT_OPERATOR){
            input = input+"0";
        }
        mShowInputTv.setText(input+key);
        addInputList(INPUT_POINT, key);
    }
    /**
     * 输入数字
     * @param view
     */
    private void inputNumber(View view){
        if(mLastInputstatus == END || mLastInputstatus == ERROR){
            clearInputScreen();
        }
        String key = map.get(view);
        if("0".equals(mShowInputTv.getText().toString())){
            mShowInputTv.setText(key);
        }else{
            mShowInputTv.setText(mShowInputTv.getText() + key);
        }
        addInputList(INPUT_NUMBER, key);
    }
    /**
     * 输入运算符
     * @param view
     */
    private void inputOperator(View view) {
        if(mLastInputstatus == INPUT_OPERATOR || mLastInputstatus == ERROR){
            return;
        }
        if(mLastInputstatus == END){
            mLastInputstatus = INPUT_NUMBER;
        }

        String key = map.get(view);
        if("0".equals(mShowInputTv.getText().toString())){
            mShowInputTv.setText("0"+key);
            mInputList.set(0,new InputItem("0",InputItem.InputType.INT_TYPE));
        }else{
            mShowInputTv.setText(mShowInputTv.getText() + key);
        }
        addInputList(INPUT_OPERATOR, key);
    }
    /**
     * 回退操作
     */
    private void back() {
        if(mLastInputstatus == ERROR){
            clearInputScreen();
        }
        String str = mShowInputTv.getText().toString();
        if(str.length() != 1){
            mShowInputTv.setText(str.substring(0, str.length()-1));
            backList();
        }else{
            mShowInputTv.setText(getResources().getString(R.string.calculator_zero));
            clearScreen(new InputItem("",InputItem.InputType.INT_TYPE));
        }
    }
    /**
     * 回退InputList操作
     */
    private void backList() {
        InputItem item = mInputList.get(mInputList.size() - 1);
        if (item.getType() == InputItem.InputType.INT_TYPE) {
            //获取到最后一个item,并去掉最后一个字符
            String input = item.getInput().substring(0,
                    item.getInput().length() - 1);
            //如果截完了，则移除这个item，并将当前状态改为运算操作符
            if ("".equals(input)) {
                mInputList.remove(item);
                mLastInputstatus = INPUT_OPERATOR;
            } else {
                //否则设置item为截取完的字符串，并将当前状态改为number
                item.setInput(input);
                mLastInputstatus = INPUT_NUMBER;
            }
            //如果item是运算操作符 则移除。
        } else if (item.getType() == InputItem.InputType.OPERATOR_TYPE) {
            mInputList.remove(item);
            if (mInputList.get(mInputList.size() - 1).getType() == InputItem.InputType.INT_TYPE) {
                mLastInputstatus = INPUT_NUMBER;
            } else {
                mLastInputstatus = INPUT_POINT;
            }
            //如果当前item是小数
        } else {
            String input = item.getInput().substring(0,
                    item.getInput().length() - 1);
            if ("".equals(input)) {
                mInputList.remove(item);
                mLastInputstatus = INPUT_OPERATOR;
            } else {
                if (input.contains(".")) {
                    item.setInput(input);
                    mLastInputstatus = INPUT_POINT;
                } else {
                    item.setInput(input);
                    mLastInputstatus = INPUT_NUMBER;
                }
            }
        }
    }
    //清理屏
    private void clearAllScreen() {
        clearResultScreen();
        clearInputScreen();
    }
    private void clearResultScreen(){
        mShowResultTv.setText("");
    }

    private void clearInputScreen() {
        mShowInputTv.setText(getResources().getString(R.string.calculator_zero));
        mLastInputstatus = INPUT_NUMBER;
        mInputList.clear();
        mInputList.add(new InputItem("", InputItem.InputType.INT_TYPE));
    }
    //计算完成
    private void clearScreen(InputItem item) {
        if(mLastInputstatus != ERROR){
            mLastInputstatus = END;
        }
        mInputList.clear();
        mInputList.add(item);
    }

    //实现高级运算
    public int findHighOperator(int index) {
        if (mInputList.size() > 1 && index >= 0 && index < mInputList.size())
            for (int i = index; i < mInputList.size(); i++) {
                InputItem item = mInputList.get(i);
                if (getResources().getString(R.string.calculator_divide).equals(item.getInput())
                        || getResources().getString(R.string.calculator_multply).equals(item.getInput())) {
                    int a,b; double c,d;
                    if(mInputList.get(i - 1).getType() == InputItem.InputType.INT_TYPE){
                        a = Integer.parseInt(mInputList.get(i - 1).getInput());
                        if(mInputList.get(i + 1).getType() == InputItem.InputType.INT_TYPE){
                            b = Integer.parseInt(mInputList.get(i + 1).getInput());
                            if(getResources().getString(R.string.calculator_multply).equals(item.getInput())){
                                mInputList.set(i - 1,new InputItem( String.valueOf(a * b),InputItem.InputType.INT_TYPE));
                            }else{
                                if(b == 0){
                                    mLastInputstatus = ERROR;
                                    if(a==0){
                                        clearScreen(new InputItem(nan, InputType.ERROR));
                                    }else{
                                        clearScreen(new InputItem(infinite,InputType.ERROR));
                                    }
                                    return -1;
                                }else if(a % b != 0){
                                    mInputList.set(i - 1,new InputItem(String.valueOf((double)a / b),InputItem.InputType.DOUBLE_TYPE));
                                }else{
                                    mInputList.set(i - 1,new InputItem(String.valueOf((Integer)a / b),InputItem.InputType.INT_TYPE));
                                }
                            }
                        }else{
                            d = Double.parseDouble(mInputList.get(i + 1).getInput());
                            if(getResources().getString(R.string.calculator_multply).equals(item.getInput())){
                                mInputList.set(i - 1,new InputItem( String.valueOf(a * d),InputItem.InputType.DOUBLE_TYPE));
                            }else{
                                if(d == 0){
                                    mLastInputstatus = ERROR;
                                    if(a==0){
                                        clearScreen(new InputItem(nan,InputType.ERROR));
                                    }else{
                                        clearScreen(new InputItem(infinite,InputType.ERROR));
                                    }
                                    return -1;
                                }
                                mInputList.set(i - 1,new InputItem(String.valueOf(a / d),InputItem.InputType.DOUBLE_TYPE));
                            }
                        }
                    }else{
                        c = Double.parseDouble(mInputList.get(i-1).getInput());
                        if(mInputList.get(i + 1).getType() == InputItem.InputType.INT_TYPE){
                            b = Integer.parseInt(mInputList.get(i + 1).getInput());
                            if(getResources().getString(R.string.calculator_multply).equals(item.getInput())){
                                mInputList.set(i - 1,new InputItem( String.valueOf(c* b),InputItem.InputType.DOUBLE_TYPE));
                            }else{
                                if(b== 0){
                                    mLastInputstatus = ERROR;
                                    if(c==0){
                                        clearScreen(new InputItem(nan,InputType.ERROR));
                                    }else{
                                        clearScreen(new InputItem(infinite,InputType.ERROR));
                                    }
                                    return -1;
                                }
                                mInputList.set(i - 1,new InputItem(String.valueOf(c / b),InputItem.InputType.DOUBLE_TYPE));
                            }
                        }else{
                            d = Double.parseDouble(mInputList.get(i + 1).getInput());
                            if(getResources().getString(R.string.calculator_multply).equals(item.getInput())){
                                mInputList.set(i - 1,new InputItem( String.valueOf(mul(c,d)),InputItem.InputType.DOUBLE_TYPE));
                            }else{
                                if(d == 0){
                                    mLastInputstatus = ERROR;
                                    if(c==0){
                                        clearScreen(new InputItem(nan,InputType.ERROR));
                                    }else{
                                        clearScreen(new InputItem(infinite,InputType.ERROR));
                                    }
                                    return -1;
                                }
                                mInputList.set(i - 1,new InputItem(String.valueOf(div(c, d)),InputItem.InputType.DOUBLE_TYPE));
                            }
                        }
                    }
                    mInputList.remove(i + 1);
                    mInputList.remove(i);
                    return findHighOperator(i);
                }
            }
        return -1;

    }

    public int findLowOperator(int index) {
        if (mInputList.size()>1 && index >= 0 && index < mInputList.size())
            for (int i = index; i < mInputList.size(); i++) {
                InputItem item = mInputList.get(i);
                if (getResources().getString(R.string.calculator_sub).equals(item.getInput())
                        || getResources().getString(R.string.calculator_add).equals(item.getInput())) {
                    int a,b; double c,d;
                    if(mInputList.get(i - 1).getType() == InputItem.InputType.INT_TYPE){
                        a = Integer.parseInt(mInputList.get(i - 1).getInput());
                        if(mInputList.get(i + 1).getType() == InputItem.InputType.INT_TYPE){
                            b = Integer.parseInt(mInputList.get(i + 1).getInput());
                            if(getResources().getString(R.string.calculator_add).equals(item.getInput())){
                                mInputList.set(i - 1,new InputItem( String.valueOf(a + b),InputItem.InputType.INT_TYPE));
                            }else{
                                mInputList.set(i - 1,new InputItem(String.valueOf(a - b),InputItem.InputType.INT_TYPE));
                            }
                        }else{
                            d = Double.parseDouble(mInputList.get(i + 1).getInput());
                            if(getResources().getString(R.string.calculator_add).equals(item.getInput())){
                                mInputList.set(i - 1,new InputItem( String.valueOf(a + d),InputItem.InputType.DOUBLE_TYPE));
                            }else{
                                mInputList.set(i - 1,new InputItem(String.valueOf(a - d),InputItem.InputType.DOUBLE_TYPE));
                            }
                        }
                    }else{
                        c = Double.parseDouble(mInputList.get(i-1).getInput());
                        if(mInputList.get(i + 1).getType() == InputItem.InputType.INT_TYPE){
                            b = Integer.parseInt(mInputList.get(i + 1).getInput());
                            if(getResources().getString(R.string.calculator_add).equals(item.getInput())){
                                mInputList.set(i - 1,new InputItem( String.valueOf(c + b),InputItem.InputType.DOUBLE_TYPE));
                            }else{
                                mInputList.set(i - 1,new InputItem(String.valueOf(c - b),InputItem.InputType.DOUBLE_TYPE));
                            }
                        }else{
                            d = Double.parseDouble(mInputList.get(i + 1).getInput());
                            if(getResources().getString(R.string.calculator_add).equals(item.getInput())){
                                mInputList.set(i - 1,new InputItem( String.valueOf(add(c, d)),InputItem.InputType.DOUBLE_TYPE));
                            }else{
                                mInputList.set(i - 1,new InputItem(String.valueOf(sub(c,d)),InputItem.InputType.DOUBLE_TYPE));
                            }
                        }
                    }
                    mInputList.remove(i + 1);
                    mInputList.remove(i);
                    return findLowOperator(i);
                }
            }
        return -1;

    }
    //currentStatus 当前状态  9  "9" "+"
    void addInputList(int currentStatus,String inputChar){
        switch (currentStatus) {
            case INPUT_NUMBER:
                if(mLastInputstatus == INPUT_NUMBER){
                    InputItem item = (InputItem)mInputList.get(mInputList.size()-1);
                    item.setInput(item.getInput()+inputChar);
                    item.setType(InputItem.InputType.INT_TYPE);
                    mLastInputstatus = INPUT_NUMBER;
                }else if(mLastInputstatus == INPUT_OPERATOR){
                    InputItem item = new InputItem(inputChar, InputItem.InputType.INT_TYPE);
                    mInputList.add(item);
                    mLastInputstatus = INPUT_NUMBER;
                }else if(mLastInputstatus == INPUT_POINT){
                    InputItem item = (InputItem)mInputList.get(mInputList.size()-1);
                    item.setInput(item.getInput()+inputChar);
                    item.setType(InputItem.InputType.DOUBLE_TYPE);
                    mLastInputstatus = INPUT_POINT;
                }
                break;
            case INPUT_OPERATOR:
                InputItem item = new InputItem(inputChar, InputItem.InputType.OPERATOR_TYPE);
                mInputList.add(item);
                mLastInputstatus = INPUT_OPERATOR;
                break;
            case INPUT_POINT://point
                if(mLastInputstatus == INPUT_OPERATOR){
                    InputItem item1 =  new InputItem("0"+inputChar,InputItem.InputType.DOUBLE_TYPE);
                    mInputList.add(item1);
                    mLastInputstatus = INPUT_POINT;
                }else{
                    InputItem item1 = (InputItem)mInputList.get(mInputList.size()-1);
                    item1.setInput(item1.getInput()+inputChar);
                    item1.setType(InputItem.InputType.DOUBLE_TYPE);
                    mLastInputstatus = INPUT_POINT;
                }
                break;
        }
    }

    public static Double div(Double v1,Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.divide(b2,10,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Double sub(Double v1,Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.subtract(b2).doubleValue();
    }

    public static Double add(Double v1,Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.add(b2).doubleValue();
    }

    public static Double mul(Double v1,Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.multiply(b2).doubleValue();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){

        public void handleMessage(Message msg) {
            if(msg.what == SHOW_RESULT_DATA){
                mShowResultTv.setText(mShowInputTv.getText());
                mShowInputTv.setText(mInputList.get(0).getInput());
                clearScreen(mInputList.get(0));
            }
        }
    };
}

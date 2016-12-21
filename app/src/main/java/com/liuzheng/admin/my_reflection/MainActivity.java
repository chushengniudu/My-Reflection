package com.liuzheng.admin.my_reflection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


/**
 * +
 * 理解Android中的注解与反射
 * http://www.jianshu.com/p/d4978bbce12a
 * http://efany.github.io/2016/04/02/Android%E6%B3%A8%E8%A7%A3%E4%B8%8E%E5%8F%8D%E5%B0%84%E6%9C%BA%E5%88%B6/
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private TestClass tc = null;
    private Class c = null;
    private Field[] fields = null;
    private Method[] ms = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);
        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(this);
        button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(this);
        button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                try {
                    //获取类
                    c = Class.forName("java.lang.String");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                // 获取所有的属性
                fields = c.getDeclaredFields();
                StringBuffer sb = new StringBuffer();
                sb.append(Modifier.toString(c.getModifiers()) + " class " + c.getSimpleName() + "{\n");
                // 遍历每一个属性
                for (Field field : fields) {
                    sb.append("\t");// 空格
                    sb.append(Modifier.toString(field.getModifiers()) + " ");// 获得属性的修饰符，例如public，static等等
                    sb.append(field.getType().getSimpleName() + " ");// 属性的类型的名字
                    sb.append(field.getName() + ";\n");// 属性的名字+回车
                }
                sb.append("}\n");
                Log.d("MainActivity", "就可以获得 String ，这个我们常用类的所有属性：" + sb);
                break;
            case R.id.button2:
                //最下面的注释
                break;
            case R.id.button3:
                try {
                    c = Class.forName("com.liuzheng.admin.my_reflection.TestClass");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                fields = c.getDeclaredFields();
                // 遍历每一个属性
                for (Field field : fields) {
                    StringBuffer mTestClasssb = new StringBuffer();
                    mTestClasssb.append("\t");// 空格
                    mTestClasssb.append(Modifier.toString(field.getModifiers()) + " ");// 获得属性的修饰符，例如public，static等等
                    mTestClasssb.append(field.getType().getSimpleName() + " ");// 属性的类型的名字
                    mTestClasssb.append(field.getName() + ";\n");// 属性的名字+回车
                    Log.d("MainActivity", "就可以获得 TestClass ，这个我们常用类的所有属性：" + mTestClasssb);
                }
                break;
            case R.id.button4:
                try {
                    c = Class.forName("com.liuzheng.admin.my_reflection.TestClass");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                // 获取所有的方法
                ms = c.getDeclaredMethods();
                break;
            case R.id.button5:
                try {
                    c = Class.forName("com.liuzheng.admin.my_reflection.TestClass");
                    //新实例
                    tc = (TestClass) c.newInstance();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                // 获取所有的属性
                fields = c.getDeclaredFields();
                //循环遍历所有的属性
                for (Field field : fields) {
                    //判断该属性是否存在注释
                    if (field.isAnnotationPresent(BindPort.class)) {
                        //获取注解的默认值,找到被BindPort修饰的属性
                        BindPort port = field.getAnnotation(BindPort.class);
                        //设置可访问(这里setAccessible(true)的使用时因为，我们在声明port变量时，其类型为private，为了确保可以访问这个变量，防止程序出现异常。)
                        field.setAccessible(true);
                        try {
                            //然后将BindPort中value的值赋给该属性
                            field.set(tc, port.value());
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    if (field.isAnnotationPresent(BindAddress.class)) {
                        BindAddress address = field.getAnnotation(BindAddress.class);
                        field.setAccessible(true);
                        try {
                            field.set(tc, address.value());
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
                tc.printInfo();
                break;
            case R.id.button6:
                try {
                    c = Class.forName("com.liuzheng.admin.my_reflection.TestClass");
                    //新实例
                    tc = (TestClass) c.newInstance();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                ms = c.getDeclaredMethods();
                for (Method method : ms) {
                    if (method.isAnnotationPresent(BindGet.class)) {
                        BindGet bindGet = method.getAnnotation(BindGet.class);
                        String param = bindGet.value();
                        try {
                            method.invoke(tc, param);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
        }
    }
    //                //获取类
//                Class b = null;
//                try {
//                    b = Class.forName("java.lang.String");
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//                // 获取所有的方法
//                Method[] ms = b.getDeclaredMethods();
//                //遍历输出所有方法
//                for (Method method : ms) {
//                    //获取方法所有参数
//                    Parameter[] parameters = method.getParameters();
//                    String params = "";
//                    if (parameters.length > 0) {
//                        StringBuffer stringBuffer = new StringBuffer();
//                        for (Parameter parameter : parameters) {
//                            stringBuffer.append(parameter.getType().getSimpleName() + " " + parameter.getName() + ",");
//                        }
//                        //去掉最后一个逗号
//                        params = stringBuffer.substring(0, stringBuffer.length() - 1);
//                    }
//                    System.err.println(Modifier.toString(method.getModifiers())
//                            + " " + method.getReturnType().getSimpleName()
//                            + " " + method.getName()
//                            + " (" + params + ")");
}

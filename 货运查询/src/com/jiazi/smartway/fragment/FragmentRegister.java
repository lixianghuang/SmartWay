package com.jiazi.smartway.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.jiazi.smartway.R;
import com.jiazi.smartway.R.array;
import com.jiazi.smartway.R.id;
import com.jiazi.smartway.R.layout;
import com.jiazi.smartway.adapter.MyArrayListAdapter;
import com.jiazi.smartway.utils.Constant;

public class FragmentRegister extends Fragment implements OnClickListener {

	private EditText et_name;
	private EditText et_psw;
	private EditText et_psw1;

	private TextView tv_user_type;

	private String[] user_type;

	private int index = 0;

	private PopupWindow popupWindow;

	private View tab_view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		tab_view = inflater.inflate(R.layout.activity_register, null);

		user_type = getResources().getStringArray(R.array.user_type);

		et_name = (EditText) tab_view.findViewById(R.id.et_reg_name);
		et_psw = (EditText) tab_view.findViewById(R.id.et_reg_psw);
		et_psw1 = (EditText) tab_view.findViewById(R.id.et_reg_psw1);

		tab_view.findViewById(R.id.ibtn_go_back).setOnClickListener(this);
		tab_view.findViewById(R.id.btn_user_register).setOnClickListener(this);

		tv_user_type = (TextView) tab_view.findViewById(R.id.tv_user_type);
		tv_user_type.setOnClickListener(this);
		return tab_view;
	}

	private void register() {
		String name = et_name.getText().toString();
		String psw = et_psw.getText().toString();
		String psw1 = et_psw1.getText().toString();

		// if (name == null || name.equals("")) {
		// Toast.makeText(getApplicationContext(), "用户名不能为空",
		// Toast.LENGTH_SHORT).show();
		// return;
		// }
		// if (psw == null || psw.equals("")) {
		// Toast.makeText(getApplicationContext(), "密码不能为空",
		// Toast.LENGTH_SHORT).show();
		// return;
		// }
		// if (psw1 == null || psw1.equals("")) {
		// Toast.makeText(getApplicationContext(), "确认密码不能为空",
		// Toast.LENGTH_SHORT).show();
		// return;
		// }
		//
		// if (!psw.equals(psw1)) {
		// Toast.makeText(getApplicationContext(), "密码不一致",
		// Toast.LENGTH_SHORT).show();
		// return;
		// }

		Intent intent = new Intent();

		intent.putExtra("name", et_name.getText().toString().trim());
		intent.putExtra("psw", et_psw.getText().toString().trim());
		getActivity().setResult(1, intent);

		Toast.makeText(getActivity(), "注册成功", Toast.LENGTH_SHORT).show();
		getActivity().finish();
	}

	public boolean onTouchEvent(MotionEvent event) {
		// 点击输入框其他的地方就关闭输入法界面
		View view = getActivity().getCurrentFocus();
		if (view != null) {
			InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
		return getActivity().onTouchEvent(event);
	}

	private void showUserTypeDialog(View view) {
		LinearLayout layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.popupwindow_listview,
				null);
		ListView list = (ListView) layout.findViewById(R.id.listView);
		MyArrayListAdapter adapter = new MyArrayListAdapter(getActivity(), Constant.ARRAY_TYPE_USER, user_type);
		list.setAdapter(adapter);

		popupWindow = new PopupWindow(view);
		// 设置弹框的宽度为布局文件的宽
		popupWindow.setWidth(view.getWidth());
		popupWindow.setHeight(LayoutParams.WRAP_CONTENT);

		// 设置一个透明的背景，不然无法实现点击弹框外，弹框消失
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置点击弹框外部，弹框消失
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.setContentView(layout);
		// 设置弹框出现的位置，在v的正下方横轴偏移textview的宽度，为了对齐~纵轴不偏移
		popupWindow.showAsDropDown(view, 0, 0);
		// listView的item点击事件
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView parent, View view, int position, long id) {
				index = position;
				tv_user_type.setText(user_type[position]);
				popupWindow.dismiss();
				popupWindow = null;
			}
		});
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.ibtn_go_back:
			getActivity().finish();
			break;
		case R.id.btn_user_register:
			register();
			break;
		case R.id.tv_user_type:
			showUserTypeDialog(view);
			break;
		default:
			break;
		}
	}
}

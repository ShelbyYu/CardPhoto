package com.xueyu.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @author 陈学宇
 * @version 创建时间：2015年5月10日 下午11:45:10
 */
public abstract class SimpleBaseAdapter<T> extends BaseAdapter {

	protected Context context;
	protected List<T> data;
	protected LayoutInflater layoutInflater;

	public SimpleBaseAdapter(Context context, List<T> data) {
		this.context = context;
		this.data = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
		layoutInflater=LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		if (position >= data.size())
			return null;
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public abstract int getItemResource();

	public abstract View getItemView(int position, View convertView,ViewHolder holder);

	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (null == convertView) {
			convertView = layoutInflater.inflate(getItemResource(), null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		return getItemView(position, convertView, holder);
	}

	public class ViewHolder {
		private SparseArray<View> views = new SparseArray<View>();
		private View convertView;

		public ViewHolder(View convertView) {
			this.convertView = convertView;
		}

		@SuppressWarnings({ "unchecked", "hiding" })
		public <T extends View> T getView(int resId) {
			View v = views.get(resId);
			if (null == v) {
				v = convertView.findViewById(resId);
				views.put(resId, v);
			}
			return (T) v;
		}
	}
	public void add(T elem) {
		data.add(elem);
		notifyDataSetChanged();
	}
	public void addAll(List<T> elem) {
		data.addAll(elem);
		notifyDataSetChanged();
	}

	public void remove(T elem) {
		data.remove(elem);
		notifyDataSetChanged();
	}

	public void remove(int index) {
		data.remove(index);
		notifyDataSetChanged();
	}
	public void removeAll() {
		data.clear();
		notifyDataSetChanged();
	}
	public void replaceAll(List<T> elem) {
		data.clear();
		data.addAll(elem);
		notifyDataSetChanged();
	}
}

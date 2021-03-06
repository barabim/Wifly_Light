/**
                Copyright (C) 2012 - 2014 Patrick Bruenn.

    This file is part of WyLight.

    Wylight is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    WiflyLight is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with WiflyLight.  If not, see <http://www.gnu.org/licenses/>. */

package de.WyLight.WyLight;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.WyLight.WyLight.R;
import de.WyLight.WyLight.library.ScriptAdapter;
import de.WyLight.WyLight.library.ScriptManagerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class SetScriptFragment extends ControlFragment {
	public static final String ITEM_POSITION = "ITEM_POSITION";

	private ListView mScriptList;
	private ScriptManagerAdapter mScriptListAdapter;

	final private OnMenuItemClickListener mAddCallback = new MenuItem.OnMenuItemClickListener() {
		public boolean onMenuItemClick(MenuItem item) {
			String now = new SimpleDateFormat("yyyyMMdd-HHmmss",
					java.util.Locale.GERMANY).format(new Date());
			mScriptListAdapter.add(now);
			mScriptList.setSelection(mScriptListAdapter.getCount() - 1);
			return true;
		}
	};

	@Override
	public int getIcon() {
		return R.drawable.ic_action_settings;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (null != data) {
			final long pNative = data.getLongExtra(
					EditScriptActivity.NATIVE_SCRIPT, 0);
			final ScriptAdapter script = new ScriptAdapter(pNative);
			if (EditScriptActivity.DO_DELETE == resultCode) {
				mScriptListAdapter.remove(script);
			}
		}
		mScriptListAdapter.notifyDataSetChanged();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup group,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_set_script, group, false);
		mScriptList = (ListView) v.findViewById(R.id.savedScripts);
		mScriptListAdapter = new ScriptManagerAdapter(getActivity()
				.getApplicationContext());
		mScriptList.setAdapter(mScriptListAdapter);
		mScriptList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View v,
							int position, long arg3) {
						mProvider.sendScript(mScriptListAdapter
								.getItem(position));
					}
				});
		mScriptList
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

					public boolean onItemLongClick(AdapterView<?> arg0, View v,
							int position, long arg3) {
						Intent i = new Intent(v.getContext(),
								EditScriptActivity.class);
						i.putExtra(EditScriptActivity.NATIVE_SCRIPT,
								mScriptListAdapter.getItem(position)
										.getNative());
						startActivityForResult(i, 0);
						return true;
					}
				});
		return v;
	}

	@Override
	public void onShow(Menu menu) {
		MenuItem add = menu.findItem(R.id.action_add);
		add.setVisible(true);
		add.setOnMenuItemClickListener(mAddCallback);
	}
}

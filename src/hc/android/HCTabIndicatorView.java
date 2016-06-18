package hc.android;

import java.awt.Color;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.ToolTipManager;
import hc.android.HCRUtil;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class HCTabIndicatorView {
	private static final Color DEFAULT_FONT_COLOR = AndroidUIUtil.WIN_UNSELECTED_FONT_COLOR;
	private LinearLayout tabButton;
	private View tabSelectedTag;
	private TextView btnTextView;
	public LinearLayout defaultLinearLayout;
	final HCTabHost tabHost;
	
	public HCTabIndicatorView(HCTabHost th){
		tabHost = th;
	}
	
	public void init(boolean isLeftToRight, boolean btnEnabled, Icon ico, String text, String tip, HCTabHost tabHost, HCTabWidget mTabWidget){
		buildTabButton(isLeftToRight, btnEnabled, ico, text, tip, tabHost, mTabWidget);
		
		defaultLinearLayout = new LinearLayout(ActivityManager.getActivity());
		defaultLinearLayout.setOrientation(LinearLayout.VERTICAL);
		
		LinearLayout padLinear = new LinearLayout(ActivityManager.getActivity());
		{
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			int pad = AndroidUIUtil.getBorderStrokeWidthInPixel();
			lp.setMargins(pad, pad, pad, 0);
			padLinear.addView(tabButton, lp);
			
		}
		{
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			defaultLinearLayout.addView(padLinear, lp);
		}
		{
			tabSelectedTag = new View(ActivityManager.getActivity());
			tabSelectedTag.setBackgroundResource(HCRUtil.getResource(HCRUtil.R_drawable_tab_item));
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT, AndroidUIUtil.getBorderStrokeWidthInPixel() * 4);
			tabSelectedTag.setEnabled(false);
			defaultLinearLayout.addView(tabSelectedTag, lp);
		}
	}
	
	public void requestFocus(){
		tabButton.requestFocus();
//		tabSelectedTag.requestFocus();
//		btnTextView.setTextColor(UIUtil.WINDOW_SELECTED_BORDER_COLOR.toAndroid());
	}
	
	public void setSelected(boolean selected){
		tabButton.setSelected(selected);
		tabSelectedTag.setEnabled(selected);
//		if(selected){
//			tabButton.requestFocus();
//		}
		
		if(selected){
			btnTextView.setTextColor(AndroidUIUtil.WINDOW_BTN_TEXT_COLOR.toAndroid());
		}else{
			btnTextView.setTextColor(DEFAULT_FONT_COLOR.toAndroid());
		}
	}
	
	private void buildTabButton(boolean isLeftToRight, boolean btnEnabled, Icon ico, String text, String tip, 
			final HCTabHost tabHost, final HCTabWidget mTabWidget){
		if(tabButton == null){
			tabButton = new LinearLayout(ActivityManager.getActivity());
		}else{
			tabButton.removeAllViews();
		}
		tabButton.setBackgroundResource(HCRUtil.getResource(HCRUtil.R_drawable_button));//tab_item
		tabButton.setFocusable(btnEnabled);
		tabButton.setFocusableInTouchMode(false);
		tabButton.setClickable(btnEnabled);

		tabButton.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(android.view.View v) {
				mTabWidget.notifySelected(defaultLinearLayout, tabHost);
			}
		});
		
        if(tip != null){
	        final JComponent jcomp = new JComponent() {
			};
			jcomp.setToolTipText(tip);
			jcomp.setPeerAdAPI(tabButton);
			tabButton.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
				@Override
				public void onFocusChange(android.view.View v, boolean hasFocus) {
					if(hasFocus){
						FocusManager.setFocusOwner(jcomp);
					}
					String tip = jcomp.getToolTipText();
					if(tip != null && tip.length() > 0){
						ToolTipManager sharedInstance = ToolTipManager.sharedInstance();
						if(hasFocus){
							sharedInstance.registerComponent(jcomp);
						}else{
							sharedInstance.unregisterComponent(jcomp);
						}
					}
				}
	        });
        }
        
		if(isLeftToRight){
			addIconAdAPI(ico);
			addTextAdAPI(text, btnEnabled);
		}else{
			addTextAdAPI(text, btnEnabled);
			addIconAdAPI(ico);
		}
	}
	
	private void addIconAdAPI(Icon ico) {
		if(ico != null){
			ImageView imageButton = new ImageView(ActivityManager.getActivity());
			
			imageButton.setImageDrawable(ico.getAdapterBitmapDrawableAdAPI(tabHost.ori_comp));
			
			LinearLayout.LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			lp.gravity = (Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL | Gravity.CENTER);
			
			tabButton.addView(imageButton, lp);
		}
	}
	
	private void addTextAdAPI(String text, boolean isEnable) {
		if(text.length() != 0){
			LinearLayout.LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0F);
			lp.gravity = (Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL | Gravity.CENTER);
			
			btnTextView = new TextView(ActivityManager.getActivity());
			btnTextView.setSingleLine(true);
			btnTextView.setMaxWidth(2048);
			
			btnTextView.setTextColor(DEFAULT_FONT_COLOR.toAndroid());
			btnTextView.setText(text);
			btnTextView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER | Gravity.CENTER_VERTICAL);
			UICore.setTextSize(btnTextView, UICore.getDefaultDialogInputFontForSystemUIOnly(), tabHost.ori_comp.getScreenAdapterAdAPI());
			
			tabButton.addView(btnTextView, lp);
		}
	}

}

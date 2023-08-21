package com.statuswa.fasttalkchat.toolsdownload.CountryCodePicker;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.statuswa.fasttalkchat.toolsdownload.BuildConfig;
import com.statuswa.fasttalkchat.toolsdownload.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

public class CountryCodePicker extends RelativeLayout {
  private static String TAG = CountryCodePicker.class.getSimpleName();

  private final String DEFAULT_COUNTRY = Locale.getDefault().getCountry();
  private static final String DEFAULT_ISO_COUNTRY = "ID";
  private static final int DEFAULT_TEXT_COLOR = 0;
  private static final int DEFAULT_BACKGROUND_COLOR = Color.TRANSPARENT;

  private int mBackgroundColor = DEFAULT_BACKGROUND_COLOR;

  private int mDefaultCountryCode;
  private String mDefaultCountryNameCode;

  //Util
  private PhoneNumberUtil mPhoneUtil;
  private PhoneNumberWatcher mPhoneNumberWatcher;
  PhoneNumberInputValidityListener mPhoneNumberInputValidityListener;

  private TextView mTvSelectedCountry;
  private TextView mRegisteredPhoneNumberTextView;
  private RelativeLayout mRlyHolder;
  private ImageView mImvArrow;
  private ImageView mImvFlag;
  private LinearLayout mLlyFlagHolder;
  private Country mSelectedCountry;
  private Country mDefaultCountry;
  private RelativeLayout mRlyClickConsumer;
  private OnClickListener mCountryCodeHolderClickListener;

  private boolean mHideNameCode = false;
  private boolean mShowFlag = true;
  private boolean mShowFullName = false;
  private boolean mUseFullName = false;
  private boolean mSelectionDialogShowSearch = true;

  private List<Country> mPreferredCountries;
  //this will be "AU,ID,US"
  private String mCountryPreference;
  private List<Country> mCustomMasterCountriesList;
  //this will be "AU,ID,US"
  private String mCustomMasterCountries;
  private boolean mKeyboardAutoPopOnSearch = true;
  private boolean mIsClickable = true;
  private CountryCodeDialog mCountryCodeDialog;

  private boolean mHidePhoneCode = false;

  private int mTextColor = DEFAULT_TEXT_COLOR;

  private int mDialogTextColor = DEFAULT_TEXT_COLOR;

  private int gravityFlag=4;

  // Font typeface
  private Typeface mTypeFace;

  private boolean mIsHintEnabled = true;
  private boolean mIsEnablePhoneNumberWatcher = true;

  private boolean mSetCountryByTimeZone = true;

  private OnCountryChangeListener mOnCountryChangeListener;

  /**
   * interface to set change listener
   */
  public interface OnCountryChangeListener {
    void onCountrySelected(Country selectedCountry);
  }

  /**
   * Interface for checking when phone number checker validity is finish.
   */
  public interface PhoneNumberInputValidityListener {
    void onFinish(CountryCodePicker ccp, boolean isValid);
  }

  public CountryCodePicker(Context context) {
    super(context);
    //if (!isInEditMode())
      init(null);
  }

  public CountryCodePicker(Context context, AttributeSet attrs) {
    super(context, attrs);
    //if (!isInEditMode())
      init(attrs);
  }

  public CountryCodePicker(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    //if (!isInEditMode())
      init(attrs);
  }

  @SuppressWarnings("unused")
  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public CountryCodePicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    //if (!isInEditMode())
      init(attrs);
  }

  private void init(AttributeSet attrs) {
    inflate(getContext(), R.layout.code_picker, this);

    mTvSelectedCountry = findViewById(R.id.selected_country_tv);
    mRlyHolder = findViewById(R.id.country_code_holder_rly);
    mImvArrow = findViewById(R.id.arrow_imv);
    mImvFlag = findViewById(R.id.flag_imv);
    mLlyFlagHolder = findViewById(R.id.flag_holder_lly);
    mRlyClickConsumer = findViewById(R.id.click_consumer_rly);

    applyCustomProperty(attrs);

    mCountryCodeHolderClickListener = new OnClickListener() {
      @Override public void onClick(View v) {
        if (isClickable()) {
          showCountryCodePickerDialog();
        }
      }
    };

    mRlyClickConsumer.setOnClickListener(mCountryCodeHolderClickListener);
  }

  private void applyCustomProperty(AttributeSet attrs) {
    mPhoneUtil = PhoneNumberUtil.createInstance(getContext());
    Resources.Theme theme = getContext().getTheme();
    TypedArray a = theme.obtainStyledAttributes(attrs, R.styleable.CountryCodePicker, 0, 0);

    try {
      mHidePhoneCode = a.getBoolean(R.styleable.CountryCodePicker_ccp_hidePhoneCode, false);
      mShowFullName = a.getBoolean(R.styleable.CountryCodePicker_ccp_showFullName, false);
      mHideNameCode = a.getBoolean(R.styleable.CountryCodePicker_ccp_hideNameCode, false);

      mIsHintEnabled = a.getBoolean(R.styleable.CountryCodePicker_ccp_enableHint, true);

      // enable auto formatter for phone number input
      mIsEnablePhoneNumberWatcher =
          a.getBoolean(R.styleable.CountryCodePicker_ccp_enablePhoneAutoFormatter, true);

      setKeyboardAutoPopOnSearch(
          a.getBoolean(R.styleable.CountryCodePicker_ccp_keyboardAutoPopOnSearch, true));

      mCustomMasterCountries = a.getString(R.styleable.CountryCodePicker_ccp_customMasterCountries);
      refreshCustomMasterList();

      gravityFlag=a.getInt(R.styleable.CountryCodePicker_ccp_countryNameGravity,4);

      mCountryPreference = a.getString(R.styleable.CountryCodePicker_ccp_countryPreference);
      refreshPreferredCountries();

      applyCustomPropertyOfDefaultCountryNameCode(a);

      showFlag(a.getBoolean(R.styleable.CountryCodePicker_ccp_showFlag, true));

      applyCustomPropertyOfColor(a);
      applyCountryNameGravity(gravityFlag);

      // text font
      String fontPath = a.getString(R.styleable.CountryCodePicker_ccp_textFont);
      if (fontPath != null && !fontPath.isEmpty()) setTypeFace(fontPath);

      //text size
      int textSize = a.getDimensionPixelSize(R.styleable.CountryCodePicker_ccp_textSize, 0);
      if (textSize > 0) {
        mTvSelectedCountry.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        setFlagSize(textSize);
        setArrowSize(textSize);
      } else { //no text size specified
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        int defaultSize = Math.round(18 * (dm.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        setTextSize(defaultSize);
      }

      //if arrow arrow size is explicitly defined
      int arrowSize = a.getDimensionPixelSize(R.styleable.CountryCodePicker_ccp_arrowSize, 0);
      if (arrowSize > 0) setArrowSize(arrowSize);

      mSelectionDialogShowSearch =
          a.getBoolean(R.styleable.CountryCodePicker_ccp_selectionDialogShowSearch, true);
      setClickable(a.getBoolean(R.styleable.CountryCodePicker_ccp_clickable, true));

      mSetCountryByTimeZone =
          a.getBoolean(R.styleable.CountryCodePicker_ccp_setCountryByTimeZone, true);

      // Set to default phone code if no country name code set in attribute.
      if (mDefaultCountryNameCode == null || mDefaultCountryNameCode.isEmpty()) {
        setDefaultCountryFlagAndCode();
      }
    } catch (Exception e) {
      Log.d(TAG, "exception = " + e.toString());
      if (isInEditMode()) {
        mTvSelectedCountry.setText(
            getContext().getString(R.string.phone_code,
                getContext().getString(R.string.country_india_code)));
      } else {
        mTvSelectedCountry.setText(e.getMessage());
      }
    } finally {
      a.recycle();
    }
  }

  private void applyCountryNameGravity(int gravityFlag) {
    if(gravityFlag==0){
      mTvSelectedCountry.setGravity(Gravity.RIGHT);
    }else if(gravityFlag==2){
      mTvSelectedCountry.setGravity(Gravity.CENTER);
    }else{
      mTvSelectedCountry.setGravity(Gravity.LEFT);
    }
  }

  private void applyCustomPropertyOfDefaultCountryNameCode(TypedArray tar) {
    //default country
    mDefaultCountryNameCode = tar.getString(R.styleable.CountryCodePicker_ccp_defaultNameCode);
    if (BuildConfig.DEBUG) {
      Log.d(TAG, "mDefaultCountryNameCode from attribute = " + mDefaultCountryNameCode);
    }

    if (mDefaultCountryNameCode == null || mDefaultCountryNameCode.isEmpty()) return;

    if (mDefaultCountryNameCode.trim().isEmpty()) {
      mDefaultCountryNameCode = null;
      return;
    }

    setDefaultCountryUsingNameCode(mDefaultCountryNameCode);
    setSelectedCountry(mDefaultCountry);
  }

  private void applyCustomPropertyOfColor(TypedArray arr) {
    //text color
    int textColor;
    if (isInEditMode()) {
      textColor = arr.getColor(R.styleable.CountryCodePicker_ccp_textColor, DEFAULT_TEXT_COLOR);
    } else {
      textColor = arr.getColor(R.styleable.CountryCodePicker_ccp_textColor,
          getColor(getContext(), R.color.black));
    }
    if (textColor != 0) setTextColor(textColor);

    mDialogTextColor =
        arr.getColor(R.styleable.CountryCodePicker_ccp_dialogTextColor, DEFAULT_TEXT_COLOR);

    // background color of view.
    mBackgroundColor =
        arr.getColor(R.styleable.CountryCodePicker_ccp_backgroundColor, Color.TRANSPARENT);

    if (mBackgroundColor != Color.TRANSPARENT) mRlyHolder.setBackgroundColor(mBackgroundColor);
  }

  private Country getDefaultCountry() {
    return mDefaultCountry;
  }

  private void setDefaultCountry(Country defaultCountry) {
    mDefaultCountry = defaultCountry;
  }

  @SuppressWarnings("unused") private Country getSelectedCountry() {
    return mSelectedCountry;
  }

  protected void setSelectedCountry(Country selectedCountry) {
    mSelectedCountry = selectedCountry;

    Context ctx = getContext();

    //as soon as country is selected, textView should be updated
    if (selectedCountry == null) {
      selectedCountry = CountryUtils.getByCode(ctx, mPreferredCountries, mDefaultCountryCode);
    }

    if (mRegisteredPhoneNumberTextView != null) {
      String ISO = selectedCountry.getIso().toUpperCase();
      setPhoneNumberWatcherToTextView(mRegisteredPhoneNumberTextView, ISO);
    }

    if (mOnCountryChangeListener != null) {
      mOnCountryChangeListener.onCountrySelected(selectedCountry);
    }

    mImvFlag.setImageResource(CountryUtils.getFlagDrawableResId(selectedCountry));

    if (mIsHintEnabled) setPhoneNumberHint();

    setSelectedCountryText(ctx, selectedCountry);
  }

  private void setSelectedCountryText(Context ctx, Country selectedCountry) {
    if (mHideNameCode && mHidePhoneCode && !mShowFullName) {
      mTvSelectedCountry.setText("");
      return;
    }

    String phoneCode = selectedCountry.getPhoneCode();
    if (mShowFullName) {
      String countryName = selectedCountry.getName().toUpperCase();

      if (mHidePhoneCode && mHideNameCode) {
        mTvSelectedCountry.setText(countryName);
        return;
      }

      if (mHideNameCode) {
        mTvSelectedCountry.setText(ctx.getString(R.string.country_full_name_and_phone_code,
            countryName, phoneCode));
        return;
      }

      String ISO = selectedCountry.getIso().toUpperCase();
      if (mHidePhoneCode) {
        mTvSelectedCountry.setText(ctx.getString(R.string.country_full_name_and_name_code,
            countryName, ISO));
        return;
      }

      mTvSelectedCountry.setText(ctx.getString(R.string.country_full_name_name_code_and_phone_code,
          countryName, ISO, phoneCode));

      return;
    }

    if (mHideNameCode && mHidePhoneCode) {
      String countryName = selectedCountry.getName().toUpperCase();
      mTvSelectedCountry.setText(countryName);
      return;
    }

    if (mHideNameCode) {
      mTvSelectedCountry.setText(ctx.getString(R.string.phone_code, phoneCode));
      return;
    }

    if (mHidePhoneCode) {
      String iso = selectedCountry.getIso().toUpperCase();
      mTvSelectedCountry.setText(iso);
      return;
    }

    String iso = selectedCountry.getIso().toUpperCase();
    mTvSelectedCountry.setText(ctx.getString(R.string.country_code_and_phone_code, iso, phoneCode));
  }

  boolean isKeyboardAutoPopOnSearch() {
    return mKeyboardAutoPopOnSearch;
  }


  public void setKeyboardAutoPopOnSearch(boolean keyboardAutoPopOnSearch) {
    mKeyboardAutoPopOnSearch = keyboardAutoPopOnSearch;
  }
 public boolean isPhoneAutoFormatterEnabled() {
    return mIsEnablePhoneNumberWatcher;
  }

   public void enablePhoneAutoFormatter(boolean isEnable) {
    mIsEnablePhoneNumberWatcher = isEnable;
    if (isEnable) {
      if (mPhoneNumberWatcher == null) {
        mPhoneNumberWatcher = new PhoneNumberWatcher(getSelectedCountryNameCode());
      }
    } else {
      mPhoneNumberWatcher = null;
    }
  }

  @SuppressWarnings("unused") private OnClickListener getCountryCodeHolderClickListener() {
    return mCountryCodeHolderClickListener;
  }


  void refreshPreferredCountries() {
    if (mCountryPreference == null || mCountryPreference.length() == 0) {
      mPreferredCountries = null;
      return;
    }

    List<Country> localCountryList = new ArrayList<>();
    for (String nameCode : mCountryPreference.split(",")) {
      Country country =
          CountryUtils.getByNameCodeFromCustomCountries(getContext(), mCustomMasterCountriesList,
              nameCode);
      if (country == null) continue;

      if (isAlreadyInList(country, localCountryList)) continue;
      localCountryList.add(country);
    }

    if (localCountryList.size() == 0) {
      mPreferredCountries = null;
    } else {
      mPreferredCountries = localCountryList;
    }
  }

  void refreshCustomMasterList() {
    if (mCustomMasterCountries == null || mCustomMasterCountries.length() == 0) {
      mCustomMasterCountriesList = null;
      return;
    }

    List<Country> localCountries = new ArrayList<>();
    String[] split = mCustomMasterCountries.split(",");
    for (int i = 0; i < split.length; i++) {
      String nameCode = split[i];
      Country country = CountryUtils.getByNameCodeFromAllCountries(getContext(), nameCode);
      if (country == null) continue;

      if (isAlreadyInList(country, localCountries)) continue;
      localCountries.add(country);
    }

    if (localCountries.size() == 0) {
      mCustomMasterCountriesList = null;
    } else {
      mCustomMasterCountriesList = localCountries;
    }
  }

  List<Country> getCustomCountries() {
    return mCustomMasterCountriesList;
  }


  List<Country> getCustomCountries(@NonNull CountryCodePicker codePicker) {
    codePicker.refreshCustomMasterList();
    if (codePicker.getCustomCountries() == null || codePicker.getCustomCountries().size() <= 0) {
      return CountryUtils.getAllCountries(codePicker.getContext());
    } else {
      return codePicker.getCustomCountries();
    }
  }


  public void setCustomMasterCountriesList(@Nullable List<Country> customMasterCountriesList) {
    mCustomMasterCountriesList = customMasterCountriesList;
  }

   public String getCustomMasterCountries() {
    return mCustomMasterCountries;
  }

  public List<Country> getPreferredCountries() {
    return mPreferredCountries;
  }


  public void setCustomMasterCountries(@Nullable String customMasterCountries) {
    mCustomMasterCountries = customMasterCountries;
  }


  private boolean isAlreadyInList(Country country, List<Country> countries) {
    if (country == null || countries == null) return false;

    for (int i = 0; i < countries.size(); i++) {
      if (countries.get(i).getIso().equalsIgnoreCase(country.getIso())) {
        return true;
      }
    }

    return false;
  }


  private String detectCarrierNumber(String fullNumber, Country country) {
    String carrierNumber;
    if (country == null || fullNumber == null) {
      carrierNumber = fullNumber;
    } else {
      int indexOfCode = fullNumber.indexOf(country.getPhoneCode());
      if (indexOfCode == -1) {
        carrierNumber = fullNumber;
      } else {
        carrierNumber = fullNumber.substring(indexOfCode + country.getPhoneCode().length());
      }
    }
    return carrierNumber;
  }

  /**
   * This method is not encouraged because this might set some other country which have same country
   * code as of yours. e.g 1 is common for US and canada.
   * If you are trying to set US ( and mCountryPreference is not set) and you pass 1 as @param
   * mDefaultCountryCode, it will set canada (prior in list due to alphabetical order)
   * Rather use setDefaultCountryUsingNameCode("us"); or setDefaultCountryUsingNameCode("US");
   * <p>
   * Default country code defines your default country.
   * Whenever invalid / improper number is found in setCountryForPhoneCode() /  setFullNumber(), it
   * CCP will set to default country.
   * This function will not set default country as selected in CCP. To set default country in CCP
   * call resetToDefaultCountry() right after this call.
   * If invalid mDefaultCountryCode is applied, it won't be changed.
   *
   * @param defaultCountryCode code of your default country
   * if you want to set IN +91(India) as default country, mDefaultCountryCode =  91
   * if you want to set JP +81(Japan) as default country, mDefaultCountryCode =  81
   */
  @Deprecated public void setDefaultCountryUsingPhoneCode(int defaultCountryCode) {
    Country defaultCountry =
        CountryUtils.getByCode(getContext(), mPreferredCountries, defaultCountryCode);

    if (defaultCountry == null) return;

    //if correct country is found, set the country
    mDefaultCountryCode = defaultCountryCode;
    setDefaultCountry(defaultCountry);
  }

  public void setDefaultCountryUsingPhoneCodeAndApply(int defaultCountryCode) {
    Country defaultCountry =
        CountryUtils.getByCode(getContext(), mPreferredCountries, defaultCountryCode);

    if (defaultCountry == null) return;

    //if correct country is found, set the country
    mDefaultCountryCode = defaultCountryCode;
    setDefaultCountry(defaultCountry);

    resetToDefaultCountry();
  }

  /**
   * Default country name code defines your default country.
   * Whenever invalid / improper name code is found in setCountryForNameCode(), CCP will set to
   * default country.
   * This function will not set default country as selected in CCP. To set default country in CCP
   * call resetToDefaultCountry() right after this call.
   * If invalid countryIso is applied, it won't be changed.
   *
   * @param countryIso code of your default country
   * if you want to set IN +91(India) as default country, countryIso =  "IN" or "in"
   * if you want to set JP +81(Japan) as default country, countryIso =  "JP" or "jp"
   */
  public void setDefaultCountryUsingNameCode(@NonNull String countryIso) {
    Country defaultCountry = CountryUtils.getByNameCodeFromAllCountries(getContext(), countryIso);

    if (defaultCountry == null) return;

    //if correct country is found, set the country
    mDefaultCountryNameCode = defaultCountry.getIso();
    setDefaultCountry(defaultCountry);
  }

  /**
   * Set default country as selected in CountryCodePicker.
   *
   * There is no change applied if invalid countryIso is given.
   *
   * @param countryIso code of your default country
   * if you want to set IN +91(India) as default country, countryIso =  "IN" or "in"
   * if you want to set JP +81(Japan) as default country, countryIso =  "JP" or "jp"
   */
  public void setDefaultCountryUsingNameCodeAndApply(@NonNull String countryIso) {
    Country defaultCountry = CountryUtils.getByNameCodeFromAllCountries(getContext(), countryIso);

    if (defaultCountry == null) return;

    //if correct country is found, set the country
    mDefaultCountryNameCode = defaultCountry.getIso();
    setDefaultCountry(defaultCountry);



    setEmptyDefault(null);
  }


  public String getDefaultCountryCode() {
    return mDefaultCountry.getPhoneCode();
  }

  public int getDefaultCountryCodeAsInt() {
    int code = 0;
    try {
      code = Integer.parseInt(getDefaultCountryCode());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return code;
  }

  public String getDefaultCountryCodeWithPlus() {
    return getContext().getString(R.string.phone_code, getDefaultCountryCode());
  }


  public String getDefaultCountryName() {
    return mDefaultCountry.getName();
  }


  public String getDefaultCountryNameCode() {
    return mDefaultCountry.getIso().toUpperCase();
  }


   public void resetToDefaultCountry() {
    setEmptyDefault();
  }


  public String getSelectedCountryCode() {
    return mSelectedCountry.getPhoneCode();
  }


  public String getSelectedCountryCodeWithPlus() {
    return getContext().getString(R.string.phone_code, getSelectedCountryCode());
  }

   public int getSelectedCountryCodeAsInt() {
    int code = 0;
    try {
      code = Integer.parseInt(getSelectedCountryCode());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return code;
  }


  public String getSelectedCountryName() {
    return mSelectedCountry.getName();
  }


  public String getSelectedCountryNameCode() {
    return mSelectedCountry.getIso().toUpperCase();
  }


  public void setCountryForPhoneCode(int countryCode) {
    Context ctx = getContext();
    Country country = CountryUtils.getByCode(ctx, mPreferredCountries, countryCode);
    if (country == null) {
      if (mDefaultCountry == null) {
        mDefaultCountry = CountryUtils.getByCode(ctx, mPreferredCountries, mDefaultCountryCode);
      }
      setSelectedCountry(mDefaultCountry);
    } else {
      setSelectedCountry(country);
    }
  }

  public void setCountryForNameCode(@NonNull String countryNameCode) {
    Context ctx = getContext();
    Country country = CountryUtils.getByNameCodeFromAllCountries(ctx, countryNameCode);
    if (country == null) {
      if (mDefaultCountry == null) {
        mDefaultCountry = CountryUtils.getByCode(ctx, mPreferredCountries, mDefaultCountryCode);
      }
      setSelectedCountry(mDefaultCountry);
    } else {
      setSelectedCountry(country);
    }
  }


  public void registerPhoneNumberTextView(@NonNull TextView textView) {
    setRegisteredPhoneNumberTextView(textView);
    if (mIsHintEnabled) setPhoneNumberHint();
  }

 public TextView getRegisteredPhoneNumberTextView() {
    return mRegisteredPhoneNumberTextView;
  }

  void setRegisteredPhoneNumberTextView(@NonNull TextView phoneNumberTextView) {
    mRegisteredPhoneNumberTextView = phoneNumberTextView;
    if (mIsEnablePhoneNumberWatcher) {
      if (mPhoneNumberWatcher == null) {
        mPhoneNumberWatcher = new PhoneNumberWatcher(getDefaultCountryNameCode());
      }
      mRegisteredPhoneNumberTextView.addTextChangedListener(mPhoneNumberWatcher);
    }
  }

  private void setPhoneNumberWatcherToTextView(TextView textView, String countryNameCode) {
    if (!mIsEnablePhoneNumberWatcher) return;

    if (mPhoneNumberWatcher == null) {
      mPhoneNumberWatcher = new PhoneNumberWatcher(countryNameCode);
      textView.addTextChangedListener(mPhoneNumberWatcher);
    } else {
      if (!mPhoneNumberWatcher.getPreviousCountryCode().equalsIgnoreCase(countryNameCode)) {
        textView.removeTextChangedListener(mPhoneNumberWatcher);
        mPhoneNumberWatcher = new PhoneNumberWatcher(countryNameCode);
        textView.addTextChangedListener(mPhoneNumberWatcher);
      }
    }
  }


  public String getFullNumber() {
    String fullNumber = mSelectedCountry.getPhoneCode();
    if (mRegisteredPhoneNumberTextView == null) {
      Log.w(TAG, getContext().getString(R.string.error_unregister_carrier_number));
    } else {
      fullNumber += mRegisteredPhoneNumberTextView.getText().toString();
    }
    return fullNumber;
  }


  public void setFullNumber(@NonNull String fullNumber) {
    Country country = CountryUtils.getByNumber(getContext(), mPreferredCountries, fullNumber);
    setSelectedCountry(country);
    String carrierNumber = detectCarrierNumber(fullNumber, country);
    if (mRegisteredPhoneNumberTextView == null) {
      Log.w(TAG, getContext().getString(R.string.error_unregister_carrier_number));
    } else {
      mRegisteredPhoneNumberTextView.setText(carrierNumber);
    }
  }


  public String getFullNumberWithPlus() {
    return getContext().getString(R.string.phone_code, getFullNumber());
  }

  public int getTextColor() {
    return mTextColor;
  }

  public int getDefaultContentColor() {
    return DEFAULT_TEXT_COLOR;
  }


  public void setTextColor(int contentColor) {
    mTextColor = contentColor;
    mTvSelectedCountry.setTextColor(contentColor);
    mImvArrow.setColorFilter(contentColor, PorterDuff.Mode.SRC_IN);
  }

  public int getBackgroundColor() {
    return mBackgroundColor;
  }

  public void setBackgroundColor(int backgroundColor) {
    mBackgroundColor = backgroundColor;
    mRlyHolder.setBackgroundColor(backgroundColor);
  }

  public int getDefaultBackgroundColor() {
    return DEFAULT_BACKGROUND_COLOR;
  }


  public void setTextSize(int textSize) {
    if (textSize > 0) {
      mTvSelectedCountry.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
      setArrowSize(textSize);
      setFlagSize(textSize);
    }
  }


  public void setArrowSize(int arrowSizeInDp) {
    if (arrowSizeInDp > 0) {
      LayoutParams params = (LayoutParams) mImvArrow.getLayoutParams();
      params.width = arrowSizeInDp;
      params.height = arrowSizeInDp;
      mImvArrow.setLayoutParams(params);
    }
  }


  public void hideNameCode(boolean hide) {
    mHideNameCode = hide;
    setSelectedCountry(mSelectedCountry);
  }


  public void setCountryPreference(@NonNull String countryPreference) {
    mCountryPreference = countryPreference;
  }

  public void setTypeFace(@NonNull Typeface typeFace) {
    mTypeFace = typeFace;
    try {
      mTvSelectedCountry.setTypeface(typeFace);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  public void setTypeFace(@NonNull String fontAssetPath) {
    try {
      Typeface typeFace = Typeface.createFromAsset(getContext().getAssets(), fontAssetPath);
      mTypeFace = typeFace;
      mTvSelectedCountry.setTypeface(typeFace);
    } catch (Exception e) {
      Log.d(TAG, "Invalid fontPath. " + e.toString());
    }
  }

  public void setTypeFace(@NonNull Typeface typeFace, int style) {
    try {
      mTvSelectedCountry.setTypeface(typeFace, style);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Typeface getTypeFace() {
    return mTypeFace;
  }


  public void setOnCountryChangeListener(@NonNull OnCountryChangeListener onCountryChangeListener) {
    mOnCountryChangeListener = onCountryChangeListener;
  }


  public void setFlagSize(int flagSize) {
    mImvFlag.getLayoutParams().height = flagSize;
    mImvFlag.requestLayout();
  }

  public void showFlag(boolean showFlag) {
    mShowFlag = showFlag;
    mLlyFlagHolder.setVisibility(showFlag ? VISIBLE : GONE);
  }


   public void showFullName(boolean show) {
    mShowFullName = show;
    setSelectedCountry(mSelectedCountry);
  }


  public boolean isSelectionDialogShowSearch() {
    return mSelectionDialogShowSearch;
  }

  public void setSelectionDialogShowSearch(boolean selectionDialogShowSearch) {
    mSelectionDialogShowSearch = selectionDialogShowSearch;
  }

  @Override public boolean isClickable() {
    return mIsClickable;
  }


  public void setClickable(boolean isClickable) {
    mIsClickable = isClickable;
    mRlyClickConsumer.setOnClickListener(isClickable ? mCountryCodeHolderClickListener : null);
    mRlyClickConsumer.setClickable(isClickable);
    mRlyClickConsumer.setEnabled(isClickable);
  }

  public boolean isHidePhoneCode() {
    return mHidePhoneCode;
  }

  public boolean isHideNameCode() {
    return mHideNameCode;
  }


  public boolean isHintEnabled() {
    return mIsHintEnabled;
  }

 public void enableHint(boolean hintEnabled) {
    mIsHintEnabled = hintEnabled;
    if (mIsHintEnabled) setPhoneNumberHint();
  }

   public void hidePhoneCode(boolean hide) {
    mHidePhoneCode = hide;
    setSelectedCountry(mSelectedCountry);
  }

  private void setPhoneNumberHint() {

    if (mRegisteredPhoneNumberTextView == null
        || mSelectedCountry == null
        || mSelectedCountry.getIso() == null) {
      return;
    }

    String iso = mSelectedCountry.getIso().toUpperCase();
    PhoneNumberUtil.PhoneNumberType mobile = PhoneNumberUtil.PhoneNumberType.MOBILE;
    Phonenumber.PhoneNumber phoneNumber = mPhoneUtil.getExampleNumberForType(iso, mobile);
    if (phoneNumber == null) {
      mRegisteredPhoneNumberTextView.setHint("");
      return;
    }

    if (BuildConfig.DEBUG) {
      Log.d(TAG, "setPhoneNumberHint called");
      Log.d(TAG, "mSelectedCountry.getIso() = " + mSelectedCountry.getIso());
      Log.d(TAG,
          "hint = " + mPhoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL));
    }

    String hint = mPhoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);

    mRegisteredPhoneNumberTextView.setHint(hint);
  }

  private class PhoneNumberWatcher extends PhoneNumberFormattingTextWatcher {
    private boolean lastValidity;
    private String previousCountryCode = "";

    String getPreviousCountryCode() {
      return previousCountryCode;
    }

     public PhoneNumberWatcher() {
      super();
    }

    //TODO solve it! support for android kitkat
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PhoneNumberWatcher(String countryCode) {
      super(countryCode);
      previousCountryCode = countryCode;
    }

    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
      super.onTextChanged(s, start, before, count);
      try {
        String iso = null;
        if (mSelectedCountry != null) iso = mSelectedCountry.getPhoneCode().toUpperCase();
        Phonenumber.PhoneNumber phoneNumber = mPhoneUtil.parse(s.toString(), iso);
        iso = mPhoneUtil.getRegionCodeForNumber(phoneNumber);
        if (iso != null) {

        }
      } catch (NumberParseException ignored) {
      }

      if (mPhoneNumberInputValidityListener != null) {
        boolean validity = isValid();
        if (validity != lastValidity) {
          mPhoneNumberInputValidityListener.onFinish(CountryCodePicker.this, validity);
        }
        lastValidity = validity;
      }
    }
  }

  public String getNumber() {
    Phonenumber.PhoneNumber phoneNumber = getPhoneNumber();

    if (phoneNumber == null) return null;

    if (mRegisteredPhoneNumberTextView == null) {
      Log.w(TAG, getContext().getString(R.string.error_unregister_carrier_number));
      return null;
    }

    return mPhoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164);
  }

   public Phonenumber.PhoneNumber getPhoneNumber() {
    try {
      String iso = null;
      if (mSelectedCountry != null) iso = mSelectedCountry.getIso().toUpperCase();
      if (mRegisteredPhoneNumberTextView == null) {
        Log.w(TAG, getContext().getString(R.string.error_unregister_carrier_number));
        return null;
      }
      return mPhoneUtil.parse(mRegisteredPhoneNumberTextView.getText().toString(), iso);
    } catch (NumberParseException ignored) {
      return null;
    }
  }

   public boolean isValid() {
    Phonenumber.PhoneNumber phoneNumber = getPhoneNumber();
    return phoneNumber != null && mPhoneUtil.isValidNumber(phoneNumber);
  }

  @SuppressWarnings("unused")
  public void setPhoneNumberInputValidityListener(PhoneNumberInputValidityListener listener) {
    mPhoneNumberInputValidityListener = listener;
  }


  private void setDefaultCountryFlagAndCode() {
    Context ctx = getContext();
    TelephonyManager manager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
    if (manager == null) {
      Log.e(TAG, "Can't access TelephonyManager. Using default county code");
      setEmptyDefault(getDefaultCountryCode());
      return;
    }

    try {
      String simCountryIso = manager.getSimCountryIso();
      if (simCountryIso == null || simCountryIso.isEmpty()) {
        String iso = manager.getNetworkCountryIso();
        if (iso == null || iso.isEmpty()) {
          enableSetCountryByTimeZone(true);
        } else {
          setEmptyDefault(iso);
          if (BuildConfig.DEBUG) Log.d(TAG, "isoNetwork = " + iso);
        }
      } else {
        setEmptyDefault(simCountryIso);
        if (BuildConfig.DEBUG) Log.d(TAG, "simCountryIso = " + simCountryIso);
      }
    } catch (Exception e) {
      Log.e(TAG, "Error when getting sim country, error = " + e.toString());
      setEmptyDefault(getDefaultCountryCode());
    }
  }


  private void setEmptyDefault() {
    setEmptyDefault(null);
  }


  private void setEmptyDefault(String countryCode) {
    if (countryCode == null || countryCode.isEmpty()) {
      if (mDefaultCountryNameCode == null || mDefaultCountryNameCode.isEmpty()) {
        if (DEFAULT_COUNTRY == null || DEFAULT_COUNTRY.isEmpty()) {
          countryCode = DEFAULT_ISO_COUNTRY;
        } else {
          countryCode = DEFAULT_COUNTRY;
        }
      } else {
        countryCode = mDefaultCountryNameCode;
      }
    }

    if (mIsEnablePhoneNumberWatcher && mPhoneNumberWatcher == null) {
      mPhoneNumberWatcher = new PhoneNumberWatcher(countryCode);
    }

    setDefaultCountryUsingNameCode(countryCode);
    setSelectedCountry(getDefaultCountry());
  }


  public void enableSetCountryByTimeZone(boolean isEnabled) {
    if (isEnabled) {
      if (mDefaultCountryNameCode != null && !mDefaultCountryNameCode.isEmpty()) return;
      if (mRegisteredPhoneNumberTextView != null) return;
      if (mSetCountryByTimeZone) {
        TimeZone tz = TimeZone.getDefault();

        if (BuildConfig.DEBUG) Log.d(TAG, "tz.getID() = " + tz.getID());
        List<String> countryIsos = CountryUtils.getCountryIsoByTimeZone(getContext(), tz.getID());

        if (countryIsos == null) {
          // If no iso country found, fallback to device locale.
          setEmptyDefault();
        } else {
          setDefaultCountryUsingNameCode(countryIsos.get(0));
          setSelectedCountry(getDefaultCountry());
        }
      }
    }
    mSetCountryByTimeZone = isEnabled;
  }

  public int getDialogTextColor() {
    return mDialogTextColor;
  }


  public void setDialogTextColor(int dialogTextColor) {
    mDialogTextColor = dialogTextColor;
  }

  public static int getColor(Context context, int id) {
    final int version = Build.VERSION.SDK_INT;
    if (version >= 23) {
      return context.getColor(id);
    } else {
      return context.getResources().getColor(id);
    }
  }

  public void showCountryCodePickerDialog() {
    if (mCountryCodeDialog == null) mCountryCodeDialog = new CountryCodeDialog(this);
    mCountryCodeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    mCountryCodeDialog.show();
  }
}

/*
 * Copyright (C) 2020-21 Application Library Engineering Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lifeofcoding.sample.slice;

import static com.lifeofcoding.cacheutlislibrary.LogUtil.debug;
import com.google.gson.reflect.TypeToken;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.Component;
import ohos.agp.components.DependentLayout;
import ohos.agp.components.RadioContainer;
import ohos.agp.components.Text;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.text.Font;
import ohos.agp.utils.Color;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.lifeofcoding.cacheutlislibrary.CacheUtils;
import com.lifeofcoding.sample.MyClass;
import com.lifeofcoding.sample.ResourceTable;


/**
 * Sample app to test the CircleProgressBar library functionality.
 */

public class MainAbilitySlice extends AbilitySlice {

    private final String Tag = MainAbilitySlice.class.getName();
    private static final String CACHE_FILE_STRING = "cache_file_string";
    private static final String CACHE_FILE_MAP = "cache_file_map";
    private static final String CACHE_FILE_LIST_MAP = "cache_file_list_map";
    private static final String CACHE_FILE_OBJECT = "cache_file_object";
    private static final String CACHE_FILE_LIST_OBJECT = "cache_file_list_object";
    private static final String CACHE_FILE_CONTENT_STRING = "CacheUtilsLibrary is a simple util library "
            + "to write and read cache files in Android.";

    private ToastDialog toast;
    private Context context;
    private DependentLayout rootLayout;
    private int radioCheckId = ResourceTable.Id_string;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        context = getContext();
        rootLayout = (DependentLayout) findComponentById(ResourceTable.Id_root);
        final RadioContainer radioGroup = (RadioContainer) findComponentById(ResourceTable.Id_radioGroup);
        radioGroup.mark(0);
        radioGroup.setMarkChangedListener(
                (radioContainer, index) -> radioCheckId =
                        radioContainer.getComponentAt(index).getId());
        findComponentById(ResourceTable.Id_write)
                .setClickedListener(writeOnClickListener);
        findComponentById(ResourceTable.Id_read)
                .setClickedListener(readOnClickListener);
    }


    private final Component.ClickedListener writeOnClickListener = v -> {
        debug(Tag, "writeOnClickListener # onClick");
        if (radioCheckId == ResourceTable.Id_string) {
            CacheUtils.writeFile(CACHE_FILE_STRING, CACHE_FILE_CONTENT_STRING);
            makeToast().setText("Write a String into cache file");
        } else if (radioCheckId == ResourceTable.Id_map) {
            CacheUtils.writeDataMapFile(CACHE_FILE_MAP, getCacheFileContentMap());
            makeToast().setText("Write a Map<String, T> into cache file");
        } else if (radioCheckId == ResourceTable.Id_listmap) {
            CacheUtils.writeDataMapsFile(CACHE_FILE_LIST_MAP, getCacheFileContentListMap());
            makeToast().setText("Write a List<Map<String, T>> into cache file");
        } else if (radioCheckId == ResourceTable.Id_object) {
            CacheUtils.writeObjectFile(CACHE_FILE_OBJECT, new MyClass("MyClass Sample1", 0));
            makeToast().setText("Write a MyClass into cache file");
        } else if (radioCheckId == ResourceTable.Id_listobject) {
            CacheUtils.writeObjectFile(CACHE_FILE_LIST_OBJECT, getCacheFileContentListObject());
            makeToast().setText("Write a List<MyClass> into cache file");
        }

        if (toast != null) {
            getTextComponent();
            toast.show();
        }
    };

    private final Component.ClickedListener readOnClickListener = new Component.ClickedListener() {
        @Override
        public void onClick(Component v) {
            makeToast();
            if (radioCheckId == ResourceTable.Id_string) {
                String fileContent = CacheUtils.readFile(CACHE_FILE_STRING);
                makeToast().setText(fileContent == null ? "No file" : fileContent);
            } else if (radioCheckId == ResourceTable.Id_map) {
                Map<String, Object> mapData = CacheUtils.readDataMapFile(CACHE_FILE_MAP);
                makeToast().setText(mapData.isEmpty() ? "No file or empty map" : mapData.toString());
            } else if (radioCheckId == ResourceTable.Id_listmap) {
                List<Map<String, Object>> listMapData = CacheUtils.readDataMapsFile(CACHE_FILE_LIST_MAP);
                makeToast().setText(listMapData.isEmpty() ? "No file or empty map" : listMapData.toString());
            } else if (radioCheckId == ResourceTable.Id_object) {
                MyClass myClassSample = CacheUtils.readObjectFile(CACHE_FILE_OBJECT, new TypeToken<MyClass>() {
                }.getType());
                makeToast().setText(myClassSample == null ? "No file" : myClassSample.toString());
            } else if (radioCheckId == ResourceTable.Id_listobject) {
                List<MyClass> myClassList = CacheUtils.readObjectFile(CACHE_FILE_LIST_OBJECT,
                        new TypeToken<List<MyClass>>() {
                        }.getType());
                makeToast().setText(myClassList == null ? "No file" : myClassList.toString());
            }

            if (toast != null && null != getTextComponent()) {
                toast.show();
            }
        }
    };

    private static Map<String, Object> getCacheFileContentMap() {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put("firstItem", "item0");
        mapData.put("secondItem", 1);
        mapData.put("thirdItem", false);
        return mapData;
    }

    private static List<Map<String, Object>> getCacheFileContentListMap() {
        List<Map<String, Object>> listMapData = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("firstItemAt" + i, "item0At" + i);
            item.put("secondItemAt" + i, 1 + i);
            item.put("thirdItemAt" + i, i % 2 == 0);
            listMapData.add(item);
        }
        return listMapData;
    }

    private static List<MyClass> getCacheFileContentListObject() {
        List<MyClass> listObject = new ArrayList<>();
        listObject.add(new MyClass("MyClass Sample1", 0));
        listObject.add(new MyClass("MyClass Sample2", 2));
        listObject.add(new MyClass("MyClass Sample3", 2));
        return listObject;
    }

    private ToastDialog makeToast() {
        if (null == toast) {
            toast = new ToastDialog(context);
            toast.setContentCustomComponent(rootLayout);
            toast.setAutoClosable(false);
            toast.setAlignment(LayoutAlignment.CENTER);
            toast.setDuration(5 * 1000);
        }
        return toast;
    }

    private Text getTextComponent() {
        Text textComponent = (Text) toast.getContentCustomComponent();
        if (null != textComponent) {
            ShapeElement shapeElement = new ShapeElement();
            shapeElement.setCornerRadius(25);
            shapeElement.setRgbColor(new RgbColor(RgbColor.fromArgbInt(Color.BLACK.getValue())));
            shapeElement.setShape(ShapeElement.RECTANGLE);
            textComponent.setBackground(shapeElement);

            textComponent.setTextColor(Color.WHITE);
            Font regularFont = new Font.Builder(textComponent.getFont().getName()).setWeight(300).build();
            textComponent.setPaddingForText(true);
            textComponent.setFont(regularFont);

            textComponent.setPadding(10, 10, 10, 10);
            textComponent.setMultipleLine(true);
            textComponent.setMaxTextLines(50);
            return textComponent;
        }
        return null;
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}

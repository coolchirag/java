package com.chirag.unitTestFramwork;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.chirag.unitTestFramwork.annotation.FieldType;
import com.chirag.unitTestFramwork.annotation.MustExist;
import com.chirag.unitTestFramwork.annotation.NoDuplicate;
import com.chirag.unitTestFramwork.annotation.NotNull;
import com.chirag.unitTestFramwork.annotation.RequestResourceMapping;
import com.chirag.unitTestFramwork.annotation.ValidTextData;
import com.chirag.unitTestFramwork.annotation.ValidTextSize;
import com.chirag.unitTestFramwork.bean.AbstractTestBean;
import com.chirag.unitTestFramwork.testData.FieldInfoBean;
import com.chirag.unitTestFramwork.util.Utility;

public class NestedTestCaseGenerator<T extends AbstractTestBean> {

	private T testCasebeanGlobal;
	private String basePackage;
	static int counter = 0;
	// private T testCaseBean;

	public NestedTestCaseGenerator(T testCasebeanGlobal, String basePackage) {
		super();
		this.testCasebeanGlobal = testCasebeanGlobal;
		this.basePackage = basePackage;
	}

	public List<T> generateTestCases() throws Exception {
		// T testCasebean = (T) testCasebeanGlobal.clone();
		T testCaseBean = (T) Utility.deepCopy(testCasebeanGlobal);
		List<T> list = processObject("", testCaseBean);
		list.add(testCasebeanGlobal);
		return list;
	}

	private List<T> processObject(String objectPath, T testCaseBean) throws Exception {
		counter++;
		List<T> list = new ArrayList<>();
		Object obj = getFieldValueByName(objectPath, testCaseBean);

		/*
		 * final FieldInfoBean fieldInfo = getFieldByName(objectPath, testCaseBean);
		 * if(fieldInfo != null) { obj = fieldInfo.getValue(); }
		 */

		if (obj != null) {
			Class clazz = obj.getClass();
			final Field[] fields = clazz.getDeclaredFields();
			if (Utility.isValidArray(fields)) {
				for (Field field : fields) {
					final Annotation[] annotations = field.getAnnotations();
					if (Utility.isValidArray(annotations)) {
						for (Annotation annotation : annotations) {
							String logMsg = "Processing Annotation : " + annotation.annotationType() + ", on field : "
									+ field.getName() + ", of Class : " + clazz;
							printLog(logMsg);

							if (annotation instanceof NoDuplicate) {
								if (generateDuplicate((NoDuplicate) annotation, callGetter(obj, field.getName()),
										testCaseBean)) {
									testCaseBean.setStatusCode(((NoDuplicate) annotation).httpStatus());
									testCaseBean.setGenerationSource("Annotation name : " + annotation.annotationType()
											+ ", Object class : " + clazz + ", fieldName : " + field.getName());
									list.add(testCaseBean);
									testCaseBean = (T) Utility.deepCopy(testCasebeanGlobal);
								}
							} else if (annotation instanceof FieldType
									&& ((FieldType) annotation).value().equals(FieldType.Type.PERMISION)) {
								callSetter(testCaseBean, field.getName(), null);
								testCaseBean.setGenerationSource("Annotation name : " + annotation.annotationType()
										+ ", Object class : " + clazz + ", fieldName : " + field.getName());
								testCaseBean.setStatusCode(403);
								list.add(testCaseBean);
								testCaseBean = (T) Utility.deepCopy(testCasebeanGlobal);
							} else {
								if (handle(annotation, obj, field.getName(), testCaseBean)) {
									testCaseBean.setGenerationSource(
											"Annotation name : " + annotation.annotationType() + ", Object class : "
													+ obj.getClass() + ", fieldName : " + field.getName());
									list.add(testCaseBean);
									testCaseBean = (T) Utility.deepCopy(testCasebeanGlobal);

								}
							}
							obj = getFieldValueByName(objectPath, testCaseBean);// Need to update this because after
																				// every field
																				// process we generate new TestCaseBean so this
																				// will also change.
						}

					}
					if (field.getType().getPackage() != null) {
						if (field.getType().getPackage().getName().startsWith(basePackage)) {
							list.addAll(processObject(Utility.appandObjectPath(objectPath, field.getName()),
									Utility.deepCopy(testCaseBean)));
						} else {
							Type type = field.getGenericType();
							if (type instanceof ParameterizedType) {
								ParameterizedType pt = (ParameterizedType) type;
								for (Type t : pt.getActualTypeArguments()) {
									if (t.getTypeName().startsWith(basePackage)) {
										list.addAll(processObject(Utility.appandObjectPath(objectPath, field.getName()),
												Utility.deepCopy(testCaseBean)));
									}
								}
							}
						}
					}
				}

			}

		} else {
			String logMsg = "Found null object : " + objectPath;
			printLog(logMsg);
		}
		return list;
	}

	private boolean handle(Annotation a, Object obj, String fieldName, T testCaseBean) throws Exception {
		boolean handled = true;
		if (a instanceof NotNull) {
			callSetter(obj, fieldName, null);
			testCaseBean.setStatusCode(((NotNull) a).httpStatus());

		} else if (a instanceof ValidTextData) {
			callSetter(obj, fieldName, ((ValidTextData) a).invalidText());
			testCaseBean.setStatusCode(((ValidTextData) a).httpStatus());

		} else if (a instanceof ValidTextSize) {
			callSetter(obj, fieldName, textGenerator(((ValidTextSize) a).max()));
			testCaseBean.setStatusCode(((ValidTextSize) a).httpStatus());

		} else if (a instanceof MustExist) {
			callSetter(obj, fieldName, null);
			testCaseBean.setStatusCode(((MustExist) a).httpStatus());

		} else {
			handled = false;
		}
		return handled;
	}

	private String textGenerator(int maxLength) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i <= maxLength; i++) {
			sb.append("a");
		}
		return sb.toString();
	}

	private boolean generateDuplicate(NoDuplicate a, Object resourceObj, T testCasebean) throws Exception {
		boolean isHandled = true;
		RequestResourceMapping[] mappings = a.value();
		if (mappings != null && mappings.length > 0) {
			for (RequestResourceMapping mapping : mappings) {

				FieldInfoBean requestField = getFieldByName(mapping.requestField(), testCasebean);
				FieldInfoBean resourceField = getFieldByName(mapping.resourceField(), resourceObj);
				if (requestField == null || resourceField == null) {
					isHandled = false;
					break;
				}
				Object requestData = callGetter(requestField.getObj(), requestField.getField().getName());// requestField.getField().get(requestField.getObj());
				callSetter(resourceField.getObj(), resourceField.getField().getName(), requestData);
			}
		} else {
			isHandled = false;
		}

		return isHandled;
	}

	private FieldInfoBean getFieldByName(String name, Object obj) throws Exception {
		String[] fields = name.split("\\.");
		Integer lastIndex = fields.length > 1 ? fields.length - 1 : 0;
		for (int index = 0; index < lastIndex; index++) {
			Field field = obj.getClass().getDeclaredField(fields[index]);
			obj = callGetter(obj, field.getName());
		}
		return new FieldInfoBean(obj.getClass().getDeclaredField(fields[lastIndex]), obj);

	}

	private Object getFieldValueByName(String name, Object obj) throws Exception {
		if (name == null || name.isEmpty()) {
			return obj;
		}
		String[] fields = name.split("\\.");
		Field field = null;
		Integer lastIndex = fields.length;
		for (int index = 0; index < lastIndex; index++) {
			if (obj == null) {
				break;
			}
			field = obj.getClass().getDeclaredField(fields[index]);
			obj = callGetter(obj, field.getName());
		}
		if (obj == null) {
			String logMsg = "No field value found by name : " + name;
			printLog(logMsg);
		}
		return obj;
	}

	private String stringGenerator(int size) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i <= size; i++) {
			sb.append("a");
		}
		return sb.toString();
	}

	/**
	 * @param obj
	 * @param fieldName
	 * @param value
	 * @throws Exception
	 */
	private void callSetter(Object obj, String fieldName, Object value) throws Exception {
		PropertyDescriptor pd;
		try {
			pd = new PropertyDescriptor(fieldName, obj.getClass());
			pd.getWriteMethod().invoke(obj, value);
		} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * @param obj
	 * @param fieldName
	 * @param value
	 * @throws Exception
	 */
	private Object callGetter(Object obj, String fieldName) throws Exception {
		PropertyDescriptor pd;
		try {
			pd = new PropertyDescriptor(fieldName, obj.getClass());
			return pd.getReadMethod().invoke(obj);
		} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	private static void printLog(String msg) {
		System.out.println(msg);
	}
}

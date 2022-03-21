/**
 *
 *
 */
package com.guilin.studycode.utils.excel;


/**
 *@Desc 
 *
 * @author guilin
 *
 * @Date
 * 
 * 请看一下这个类都有哪些功能:
 * 1.实体属性配置了注解就能导出到excel中,每个属性都对应一列.
 * 2.列名称可以通过注解配置.
 * 3.导出到哪一列可以通过注解配置.
 * 4.鼠标移动到该列时提示信息可以通过注解配置.
 * 5.用注解设置只能下拉选择不能随意填写功能.
 * 6.用注解设置是否只导出标题而不导出内容,这在导出内容作为模板以供用户填写时比较实用.
 * https://blog.csdn.net/lk_blog/article/details/8007777

 */


public class StudentVO {
	
		@ExcelVOAttribute(name = "序号", column = "A")
		private int id;
	 
		@ExcelVOAttribute(name = "姓名", column = "B", isExport = true)
		private String name;
	 
		@ExcelVOAttribute(name = "年龄", column = "C", prompt = "年龄保密哦!", isExport = false)
		private int age;
	 
		@ExcelVOAttribute(name = "班级", column = "D", combo = { "五期提高班", "六期提高班",
				"七期提高班" })
		private String clazz;
	 
		@ExcelVOAttribute(name = "公司", column = "E")
		private String company;
		
		@ExcelVOAttribute(name = "地址", column = "F")
		private String address;
		
		@ExcelVOAttribute(name = "身份证", column = "G")
		private String cen;


		/**
		 * 
		 */
		public StudentVO() {
			super();
			// TODO Auto-generated constructor stub
		}

		/**
		 * @param id
		 * @param name
		 * @param age
		 * @param clazz
		 * @param company
		 * @param address
		 * @param cen
		 */
		public StudentVO(int id, String name, int age, String clazz, String company, String address, String cen) {
			super();
			this.id = id;
			this.name = name;
			this.age = age;
			this.clazz = clazz;
			this.company = company;
			this.address = address;
			this.cen = cen;
		}

		@Override
		public String toString() {
			return "StudentVO [id=" + id + ", name=" + name + ", age=" + age + ", clazz=" + clazz + ", company="
					+ company + ", address=" + address + ", cen=" + cen + "]";
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getClazz() {
			return clazz;
		}

		public void setClazz(String clazz) {
			this.clazz = clazz;
		}

		public String getCompany() {
			return company;
		}

		public void setCompany(String company) {
			this.company = company;
		}
		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getCen() {
			return cen;
		}

		public void setCen(String cen) {
			this.cen = cen;
		}
	 
		
	}

	
	
	
	


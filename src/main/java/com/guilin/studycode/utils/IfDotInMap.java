package com.guilin.studycode.utils;

import com.alibaba.fastjson.JSONArray;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据坐标的经纬度归属所在的区域
 * @author pugilin
 * @date: 2018年09月28日
 *  **************************************************************************************************
 *  * 程序名 : IfDotInMap.java
 *  * 建立日期: 2018-08-08
 *  * 作者 : pugilin
 *  * 模块 : 触点中心
 *  * 描述 : 江西中台项目触点中心判断点是否在图形区域内
 *  * 备注 :
 *  *
 *  *  修改历史
 *  *  序号 	                 日期 		            修改人 		          修改原因
 *  *   1      20180808     	ZZJ       江西中台项目触点中心判断点是否在图形区域内
 *  **************************************************************************************************
 */

public class IfDotInMap {

	private static double EARTH_RADIUS = 6378.137;

	public static void main(String[] args) {
		// 被检测的经纬度点
		Map<String, String> orderLocation = new HashMap<String, String>();
		orderLocation.put("lng", "117.228117");
		orderLocation.put("lat", "31.830429");
		// 区域（百度多边形区域经纬度集合）
//		String partitionLocation = "31.839064_117.219116,31.83253_117.219403,31.828511_117.218146,31.826763_117.219259,31.826118_117.220517,31.822713_117.23586,31.822958_117.238375,31.838512_117.23798,31.839617_117.226194,31.839586_117.222925";
//		System.out.println(isInPolygon(orderLocation, partitionLocation));
	}

	/** 
	 * 判断当前位置是否在多边形区域内 
	 * @param orderLocation 当前点
	 * @param partitionLocation 区域顶点
	 * @return 
     */ 
	public static boolean isInPolygon(Map<String, Object> orderLocation, JSONArray partitionLocation){

		double p_x = (double) orderLocation.get("lng");  
		double p_y = (double) orderLocation.get("lat");  
        Point2D.Double point = new Point2D.Double(p_x, p_y);  
 
        List<Point2D.Double> pointList= new ArrayList<Point2D.Double>(); 
        for (int j = 0; j < partitionLocation.size(); j++) {
        	Map<String, Object> orderAreaMap = (Map<String, Object>) partitionLocation.get(j);
        	double polygonPoint_x = (double) orderAreaMap.get("lng");   
 		    double polygonPoint_y = (double) orderAreaMap.get("lat"); 
 		    Point2D.Double polygonPoint = new Point2D.Double(polygonPoint_x,polygonPoint_y);  
		    pointList.add(polygonPoint);
		}
        return IsPtInPoly(point,pointList); 
	}  

	
	/** 
	 * 判断点是否在多边形内，如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true
	 * @param point 检测点 
	 * @param pts   多边形的顶点 
	 * @return      点在多边形内返回true,否则返回false 
	 */  
	public static boolean IsPtInPoly(Point2D.Double point, List<Point2D.Double> pts){  
         
		int N = pts.size();  
		boolean boundOrVertex = true; //如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true  
		int intersectCount = 0;//cross points count of x   
		double precision = 2e-10; //浮点类型计算时候与0比较时候的容差  
		Point2D.Double p1, p2;//neighbour bound vertices  
		Point2D.Double p = point; //当前点  
 
		p1 = pts.get(0);//left vertex          
		for(int i = 1; i <= N; ++i){//check all rays              
			if(p.equals(p1)){  
				return boundOrVertex;//p is an vertex  
			}  
             
			p2 = pts.get(i % N);//right vertex  
			//ray is outside of our interests 
			if(p.x < Math.min(p1.x, p2.x) || p.x > Math.max(p1.x, p2.x)){                 
				p1 = p2;   
				continue;//next ray left point  
			}  
             
			//ray is crossing over by the algorithm (common part of)  
			if(p.x > Math.min(p1.x, p2.x) && p.x < Math.max(p1.x, p2.x)){
				if(p.y <= Math.max(p1.y, p2.y)){//x is before of ray                      
					if(p1.x == p2.x && p.y >= Math.min(p1.y, p2.y)){//overlies on a horizontal ray
						return boundOrVertex;  
					}  
                     
					if(p1.y == p2.y){//ray is vertical                          
						if(p1.y == p.y){//overlies on a vertical ray  
							return boundOrVertex;  
						}else{//before ray  
                         	 ++intersectCount;  
						}   
					}else{//cross point on the left side                        
						//cross point of y
						double xinters = (p.x - p1.x) * (p2.y - p1.y) / (p2.x - p1.x) + p1.y;                          
						if(Math.abs(p.y - xinters) < precision){//overlies on a ray  
							return boundOrVertex;  
						}  
                         
						if(p.y < xinters){//before ray  
							++intersectCount;  
						}   
					}  
				}  
			} else {//special case when ray is crossing through the vertex                  
				if(p.x == p2.x && p.y <= p2.y){//p crossing over p2                      
					Point2D.Double p3 = pts.get((i+1) % N); //next vertex   
					//p.x lies between p1.x & p3.x  
					if(p.x >= Math.min(p1.x, p3.x) && p.x <= Math.max(p1.x, p3.x)){
						++intersectCount;  
					}else{  
						intersectCount += 2;  
					}  
				}  
			}              
			p1 = p2;//next ray left point  
		}  
         
		if(intersectCount % 2 == 0){//偶数在多边形外  
			return false;  
		} else { //奇数在多边形内  
			return true;  
		}  
	} 
	
	/** 
	 * 判断当前位置是否在圆形区域内 
	 * @param areaMap 当前点
	 * @param mapPath 圆心点
	 * @param radius 半径
	 * @return 
     */ 
    public static boolean isInCircle(Map<String,Object> areaMap,JSONArray mapPath,String radius) {
    	
    	double loc_x = (double) areaMap.get("lng");  
		double loc_y = (double) areaMap.get("lat");
		
		Map<String, Object> circleMap = (Map<String, Object>) mapPath.get(0);
    	double circle_x = (double) circleMap.get("lng");   
	    double circle_y = (double) circleMap.get("lat"); 
		
        double distance = getDistance(loc_x, loc_y, circle_x, circle_y);
        double r = Double.parseDouble(radius);
        if (distance > r) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * 通过经纬度获取距离(单位：米)
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        return s;
    }
    
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

}

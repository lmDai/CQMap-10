> http://apim.uiho.com/login.html

* temp10@uiho.com
* 123123

* http://www.tianditu.com/service/query.html

* https://developers.arcgis.com/android/

* https://github.com/AshinJiang/ArcGIS-Android-Cluster

* https://github.com/AshinJiang/ArcGIS-Android-TianDiTu

>地图服务的查询均调用arcgis的query查询方法。
 android方法：
 com.esri.core.tasks.query.QueryTask 下面传入url地址。
 com.esri.core.tasks.query.QueryParameters 设置查询条件。
 * 1、单位查询
 地址：http://www.map023.cn:6080/arcgis/rest/services/UnitService/MapServer/0
 查询条件：
 whereClause 设置成  "UNITID in (a,b,c,d,)"，abcd均是单位的id即：companyId，用逗号分开，必须加括号
 returnGeometry 设置YES
 outSpatialReference 设置 mapView的spatialReference
 * 2、分发查询
 地址：http://www.map023.cn:6080/arcgis/rest/services/polygonCG/MapServer/0/query
 whereClause 设置成 @"1=1 and FRUITID in (a,b,c,d)"，abcd均是fruitId（即报件查询下面FruitCategoryList 对象下的FruitList里的fruitId）。
 returnGeometry 设置YES
 outSpatialReference 设置 mapView的spatialReference

 * http://services.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer

 >条件查询（空间查询上面那个按钮），是正查，也就是查询出来先展示底部可滑动菜单，当选择菜单中的报件时，在地图上展示。
  空间查询必须在选择分类查询后，才能使用（也就是说地图上有数据时，再用空间查询）；
  分类查询是反查，也就是查询服务器查询出来的数据，先别展示，然后马上去查询地图数据，然后把所有数据在地图上展示，当点击地图上一个数据时，就展示底部菜单，并且菜单里面的数据显示所点中的地图数据。



 图幅号这个，是先查询地图数据，有7个链接地址，分别是
   http://ddk.digitalcq.com:6080/arcgis/rest/services/CQGRID_2000/Mapserver/0/query
   http://ddk.digitalcq.com:6080/arcgis/rest/services/CQGRID_2000/Mapserver/1/query
   http://ddk.digitalcq.com:6080/arcgis/rest/services/CQGRID_2000/Mapserver/2/query
   http://ddk.digitalcq.com:6080/arcgis/rest/services/CQGRID_2000/Mapserver/3/query
   http://ddk.digitalcq.com:6080/arcgis/rest/services/CQGRID_2000/Mapserver/4/query
   http://ddk.digitalcq.com:6080/arcgis/rest/services/CQGRID_2000/Mapserver/5/query
   http://ddk.digitalcq.com:6080/arcgis/rest/services/CQGRID_2000/Mapserver/6/query

   这种方式去查询，7个地址中有数据就展示出来。此时会在地图中画出一个范围，然后再点击确认时，用空间查询（矩形、多边形、缓冲区）的方式筛选结果

  -------
  * http://www.map023.cn:8080/admin
  * admin  111111
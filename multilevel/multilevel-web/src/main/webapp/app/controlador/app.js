var app = angular.module("miApp", [ "ngRoute", "ngStorage" ]);

app.config(function($routeProvider) {
	$routeProvider.when("/", {
		controller : "modulo1Controller",
		controllerAs : "m1",
		
	}).when("/cursos", {
		controller : "inventarioController",
		controllerAs : "cur",
		templateUrl : "vistas/cursos.html"
	}).when("/Compras", {
	    controller : "comprasController",
	    controllerAs : "cur0",
		templateUrl : "vistas/Compras.html"
	})
	.when("/Promociones", {
		controller : "promoController",
//		controllerAs : "cur1",
		templateUrl : "vistas/Promociones.html"
	}).when("/Afiliado", {
//		controller : "inventarioController",
//		controllerAs : "cur2",
		templateUrl : "vistas/Afiliado.html"
	}).when("/Pedido", {
//		controller : "inventarioController",
//		controllerAs : "cur3",
		templateUrl : "vistas/Pedido.html"
	}).when("/Reportes", {
		controller : "reporController",
//		controllerAs : "cur3",
		templateUrl : "vistas/reportes.html"
	})
	
});

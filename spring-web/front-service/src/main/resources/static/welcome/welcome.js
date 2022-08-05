angular.module('market-front').controller('welcomeController', function ($scope, $http) {
    const contextPath = 'http://localhost:5555/';

    $scope.loadFavoritesProducts = function (service) {
        $http({
            url: contextPath + 'analytic/api/v1/analytic/' + service + '/get_best_products/' + 5 + '/' + 5,
            method: 'GET',
        }).then(function (response) {
            if (service == "cart") {
                $scope.ProductsListCart = response.data;
            }
            if (service == "orders") {
                $scope.ProductsListOrders = response.data;
            }
        });
    };

    $scope.loadFavoritesProducts("cart");
    $scope.loadFavoritesProducts("orders");
});
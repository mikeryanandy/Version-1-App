function Hello($scope, $http) {
    $http.get('http://localhost:8080/retrieve').
        success(function(data) {
            $scope.retrieve = data;
        });
}

function List($scope, $http) {
    $http.get('http://localhost:8080/list').
        success(function(data) {
            $scope.retrieve = data;
        });
}
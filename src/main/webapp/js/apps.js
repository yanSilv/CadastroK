angular.module("cadUsuario", []);

angular.value('urlBase', 'http://localhost:8080/sProduto/rest/');

angular.module("cadUsuario").controller("cadUsuarioCtl", function ($scope, $window, $http, urlBase){
    $scope.login = [{nome: "", senha:""}];
    $scope.cad = [{nome: "", idade:"", endereco:"", telefone:""}];
    
    $scope.logarUser = function(login) {
        console.log(login.nome);
        $window.location.href = 'exibicao.html';
        $http.get(urlBase + "consultadata/", {params: {
                data: Time(),
                validos: consulta.validos,
                contigencia: consulta.contigencia,
                filia: consulta.tx_filial,
                caixa: consulta.tx_caixa
            }}).then(function (response) {
            $scope.objeto = response.data;
            $scope.totalItems = $scope.objeto.length;
            $scope.alimentaTable = $scope.objeto;

            if ($scope.totalItems === 0) {
                $scope.retornoConsulta = "Erro";
            } else {
                $scope.retornoConsulta = "Ok";
            }
        });
    };
    
    $scope.enviadados = function(cad) {
        console.log("Chegou aqui;;;;");
        console.log(cad.nome);
        $http.get(urlBase + "consultadata/", {params: {
                data: Time(),
                validos: consulta.validos,
                contigencia: consulta.contigencia,
                filia: consulta.tx_filial,
                caixa: consulta.tx_caixa
            }}).then(function (response) {
            $scope.objeto = response.data;
            $scope.totalItems = $scope.objeto.length;
            $scope.alimentaTable = $scope.objeto;

            if ($scope.totalItems === 0) {
                $scope.retornoConsulta = "Erro";
            } else {
                $scope.retornoConsulta = "Ok";
            }
        });
        
    };
});



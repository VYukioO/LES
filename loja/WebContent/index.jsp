<%@page language="java" contentType="txt/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="javax.security.auth.message.callback.PrivateKeyCallback.Request" %>
<%@page import="br.edu.fatec.les.dominio.EntidadeDominio" %>
<%@page import="br.edu.fatec.les.dominio.modelo.Usuario" %>
<%@page import="br.edu.fatec.les.facade.Resultado" %>
<!DOCTYPE html>
<html xmlns="http:/www.w3.org/1999/xhtml" lang="pt-br" xml:lang="pt-br">

<head>
    <title>Loja</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="./css/index.css" />
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.1/css/all.css" integrity="sha384-O8whS3fhG2OnA5Kas0Y9l3cfpmYjapjI0E4theH4iuMD+pLhbf6JI0jIMfYcK3yZ" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>

<body>
    <nav class="navbar navbar-expand-sm navbar-light bg-light">
        <div class="container">
            <a class="navbar-brand" href="#">Loja</a>
            <button class="navbar-toggler d-lg-none" type="button" data-toggle="collapse" data-target="#collapsibleNavId" aria-controls="collapsibleNavId" aria-expanded="false" aria-label="Toggle navigation"></button>
            <div class="collapse navbar-collapse" id="collapsibleNavId">
                <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                    <li class="nav-item active">
                        <a class="nav-link" href="#">InÃ­cio<span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="dropdownId" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Categorias</a>
                        <div class="dropdown-menu" aria-labelledby="dropdownId">
                            <a class="dropdown-item" href="#">AcessÃ³rios</a>
                            <a class="dropdown-item" href="#">Equipamentos</a>
                        </div>
                    </li>
                </ul>
                <ul class="navbar-nav ml-auto">
                    <a href="login.html" class="btn btn-outline-primary ml-2">Entrar</a>
                    <a href="./dashboard/dashboard.html" class="btn btn-outline-secondary ml-2">Meu Menu</a>
                </ul>
            </div>
        </div>
    </nav>

    <main class="mt-5">
        <div class="container ">
            <div class="row align-items-center mb-4">
                <div class="col-md-8 px-0">
                    <h1>Loja</h1>
                    <!-- <h2 class="h4">Lorem ipsum dolor sit amet.</h2> -->
                </div>
                <div class="col-md-4 px-0 text-right">
                    <a href="cart.html"><i class="fas fa-cart-arrow-down fa-lg mr-2"></i>Meu carrinho</a>
                </div>
            </div>

            <div class="row">
                <div class="col-md-3 py-3 border ">
                    <h3 class="font-weight-bold text-center">O que estÃ¡ procurando?</h3>
                    <form method="GET" action="/search">
                        <div class="form-group">
                            <label for="name">Nome</label>
                            <input type="text" class="form-control" name="name" id="name" aria-describedby="helpId" placeholder="">
                        </div>
                        <div class="form-group">
                            <label for="name">DescriÃ§Ã£o</label>
                            <input type="text" class="form-control" name="name" id="name" aria-describedby="helpId" placeholder="">
                        </div>
                        <div class="form-group">
                            <label for="sportType">Modalidade</label>
                            <select class="form-control" name="sportType" id="sportType">
                            <option value="" selected>Selecione</option>
                            <option value="FUTEBOL">Futebol</option>
                            <option value="BASQUETE">Basquete</option>
                            <option value="VOLEI">VÃ´lei</option>
                            <option value="TENIS">TÃªnis</option>
                          </select>
                        </div>
                        <div class="form-group">
                            <label for="category">Categoria</label>
                            <select class="form-control" name="category" id="category">
                              <option value="" selected>Selecione</option>
                              <option value="ROUPAS">Roupas</option>
                              <option value="ACESSORIOS">AcessÃ³rios</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="formControlRange">PreÃ§o</label>
                            <input type="range" min="0" max="1000" step="50" class="form-control-range" id="formControlRange" onInput="$('#rangeval').html($(this).val())">
                            <span id="rangeval">R$ 0,00</span>
                        </div>
                        <button type="submit" class="btn btn-primary btn-lg btn-block">Pesquisar</button>

                    </form>
                </div>
                <div class="col-md-9 ">
                    <div class="row px-2">
                        <div class="col-md-3 px-1">
                            <div class="card text-left">
                                <img class="card-img-top" height="150px" src="https://images.lojanike.com.br/1024x1024/produto/tenis-nike-revolution-5-masculino-BQ3204-400-1.jpg" alt="">
                                <div class="card-body px-2">
                                    <span class="badge badge-primary">Corrida</span>
                                    <h4 class="card-title mb-0">TÃªnis</h4>
                                    <small>CalÃ§ados</small>
                                    <h5 class="text-success">R$ 00,00</h5>
                                </div>
                                <div class="card-footer py-1">
                                    <div class="row justify-content-between">
                                        <a href=""><i class="fas fa-cart-plus"></i></a>
                                        <a href="">Ver mais</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3  px-1">
                            <div class="card text-left">
                                <img class="card-img-top" height="150px" src="https://images.lojanike.com.br/1024x1024/produto/tenis-nike-revolution-5-masculino-BQ3204-400-1.jpg" alt="">
                                <div class="card-body px-2">
                                    <span class="badge badge-primary">Corrida</span>
                                    <h4 class="card-title mb-0">TÃªnis</h4>
                                    <small>CalÃ§ados</small>
                                    <h5 class="text-success">R$ 00,00</h5>
                                </div>
                                <div class="card-footer py-1">
                                    <div class="row justify-content-between">
                                        <a href=""><i class="fas fa-cart-plus"></i></a>
                                        <a href="">Ver mais</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3 px-1">
                            <div class="card text-left">
                                <img class="card-img-top" height="150px" src="https://images.lojanike.com.br/1024x1024/produto/tenis-nike-revolution-5-masculino-BQ3204-400-1.jpg" alt="">
                                <div class="card-body px-2">
                                    <span class="badge badge-primary">Corrida</span>
                                    <h4 class="card-title mb-0">TÃªnis</h4>
                                    <small>CalÃ§ados</small>
                                    <h5 class="text-success">R$ 00,00</h5>
                                </div>
                                <div class="card-footer py-1">
                                    <div class="row justify-content-between">
                                        <a href=""><i class="fas fa-cart-plus"></i></a>
                                        <a href="">Ver mais</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3 px-1">
                            <div class="card text-left">
                                <img class="card-img-top" height="150px" src="https://images.lojanike.com.br/1024x1024/produto/tenis-nike-revolution-5-masculino-BQ3204-400-1.jpg" alt="">
                                <div class="card-body px-2">
                                    <span class="badge badge-primary">Corrida</span>
                                    <h4 class="card-title mb-0">TÃªnis</h4>
                                    <small>CalÃ§ados</small>
                                    <h5 class="text-success">R$ 00,00</h5>
                                </div>
                                <div class="card-footer py-1">
                                    <div class="row justify-content-between">
                                        <a href=""><i class="fas fa-cart-plus"></i></a>
                                        <a href="">Ver mais</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </main>


    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <script defer src="https://use.fontawesome.com/releases/v5.1.1/js/all.js" integrity="sha384-BtvRZcyfv4r0x/phJt9Y9HhnN5ur1Z+kZbKVgzVBAlQZX4jvAuImlIz+bG7TS00a" crossorigin="anonymous"></script>
</body>

</html>
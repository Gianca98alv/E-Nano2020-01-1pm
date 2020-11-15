
%client
:- module(client, [postClient/2]).

:- use_module(library(http/http_client)).



postClient(Reply, Body):- http_post('http://localhost:9000/compile', atom('application/json', Body), Reply,[method(post)]).
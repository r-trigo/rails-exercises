Rails.application.routes.draw do

  require 'api_constraints'


  root 'welcome#index'

  resources :armarios do #model/model
    resources :fotos
    collection do #model/id/model
      resources :welcome
      get :autocomplete
    end
  end
  resources :pts do
    collection {
      get :autocomplete
    }
  end
  #get '/welcome' => 'welcome#index'


  namespace :api, defaults: {format: 'json'} do
  #namespace :api, path: '/', constraints: { subdomain: 'api' } do

    scope module: :v1, constraints: ApiConstraints.new(version: 1) do
      resources :fotos
      resources :armarios
    end

    scope module: :v2, constraints: ApiConstraints.new(version: 2, default: true) do
      resources :armarios do
        resources :fotos
      end
    end

  end

# For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
end

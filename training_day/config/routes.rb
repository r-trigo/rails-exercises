Rails.application.routes.draw do

  require 'api_constraints'

  root 'armarios#index'

  resources :armarios do
    resources :fotos
  end
  resources :pts

  namespace :api, defaults: {format: 'json'} do
  #namespace :api, path: '/', constraints: { subdomain: 'api' } do

    scope module: :v1, constraints: ApiConstraints.new(version: 1) do
      resources :fotos
      resources :armarios
    end

    scope module: :v2, constraints: ApiConstraints.new(version: 2, default: true) do
      resources :fotos
      resources :armarios
    end

  end

# For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
end

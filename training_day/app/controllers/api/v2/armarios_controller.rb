module Api
  module V2
    class ArmariosController < ApplicationController
      #respond_to :json

      def index
        render json: Armario.all
      end

      def show
        render json: Armario.find(params[:id])
      end

      def create
        render json: Armario.create(params[:armario])
      end

      def update
        render json: Armario.update(params[:id], params[:armario])
      end

      def destroy
        render json: Armario.destroy(params[:id])
      end

    end
  end
end
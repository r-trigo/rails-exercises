module Api
  module V1
    class ArmariosController < ApplicationController
      #respond_to :json

      def index
        render json: Armario.all
      end

      def show
        render json: Armario.find(params[:id])
      end

      def create
        render json: Armario.create(armario_params)
      end

      def update
        render json: Armario.update(armario_params)
      end

      def destroy
        render json: Armario.destroy(params[:id])
      end

      private
      def armario_params
        params.require(:armario).permit(:ci_armario)
      end
    end
  end
end
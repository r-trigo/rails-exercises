module Api
  module V2
    class ArmariosController < ApplicationController

      def index
        @armarios = Armario.all
      end

      def show
        @armario = Armario.find(params[:id])
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

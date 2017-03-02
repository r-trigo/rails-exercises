class RegionsController < ApplicationController

  def index
    @regions = Region.order(:name).where("name like ?", "%#{ params[:term] }%")
    render json: @categories.map(&:name)
  end

end
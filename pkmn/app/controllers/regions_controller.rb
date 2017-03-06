class RegionsController < ApplicationController

  def index
    @regions = Region.order(:name).where("name like ?", "%#{ params[:term] }%")
    render json: @regions.map(&:name)
  end

end
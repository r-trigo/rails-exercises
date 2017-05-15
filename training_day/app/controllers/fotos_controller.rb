class FotosController < ApplicationController
  before_filter :load_imageable

  # GET /fotos
  # GET /fotos.json
  def index
    #@fotos = Foto.all
    @fotos = @imageable.fotos
  end

  # GET /fotos/new
  def new
      @foto = @imageable.fotos.new
  end

  def create
    @foto = @imageable.fotos.new(foto_params)
    if @foto.save
      redirect_to [@imageable, :fotos], notice: 'Foto criada.'
    else
      render :new
    end
  end

  private
    def load_imageable
      resource, id = request.path.split('/')[1,2]
      @imageable = resource.singularize.classify.constantize.find(id)
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def foto_params
      params.require(:foto).permit(:nome, :image, :imageable_id, :imageable_type)
    end
end

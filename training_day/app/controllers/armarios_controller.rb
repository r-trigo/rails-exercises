class ArmariosController < ApplicationController
  before_action :set_armario, only: [:show, :edit, :update, :destroy]

  def autocomplete
    render json: Autocomplete::Armarios.new(view_context)
  end

  # GET /armarios
  # GET /armarios.json
  def index
    @armarios = Armario.all
  end

  # GET /armarios/1
  # GET /armarios/1.json
  def show
  end

  # GET /armarios/new
  def new
    @armario = Armario.new
  end

  # GET /armarios/1/edit
  def edit
  end

  # POST /armarios
  # POST /armarios.json
  def create
    @armario = Armario.new(armario_params)

    respond_to do |format|
      if @armario.save
        format.html { redirect_to @armario, notice: 'Armario was successfully created.' }
        #format.json { render :show, status: :created, location: @armario }
      else
        format.html { render :new }
        #format.json { render json: @armario.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /armarios/1
  # PATCH/PUT /armarios/1.json
  def update
    respond_to do |format|
      if @armario.update(armario_params)
        format.html { redirect_to @armario, notice: 'Armario was successfully updated.' }
        format.json { render :show, status: :ok, location: @armario }
      else
        format.html { render :edit }
        format.json { render json: @armario.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /armarios/1
  # DELETE /armarios/1.json
  def destroy
    @armario.destroy
    respond_to do |format|
      format.html { redirect_to armarios_url, notice: 'Armario was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_armario
      @armario = Armario.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def armario_params
      params.require(:armario).permit(:ci_armario, fotos_attributes: Foto.attribute_names.map(&:to_sym).push(:_destroy))
    end
end

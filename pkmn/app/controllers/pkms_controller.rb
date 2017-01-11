class PkmsController < ApplicationController
  before_action :set_pkm, only: [:show, :edit, :update, :destroy]

  # GET /pkms
  # GET /pkms.json
  def index
    @pkms = Pkm.all.search(params[:region_id])
  end

  # GET /pkms/1
  # GET /pkms/1.json
  def show
  end

  # GET /pkms/new
  def new
    @pkm = Pkm.new
  end

  # GET /pkms/1/edit
  def edit
  end

  # POST /pkms
  # POST /pkms.json
  def create
    @pkm = Pkm.new(pkm_params)

    respond_to do |format|
      if @pkm.save
        format.html { redirect_to @pkm, notice: 'Pkm was successfully created.' }
        format.json { render :show, status: :created, location: @pkm }
      else
        format.html { render :new }
        format.json { render json: @pkm.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /pkms/1
  # PATCH/PUT /pkms/1.json
  def update
    respond_to do |format|
      if @pkm.update(pkm_params)
        format.html { redirect_to @pkm, notice: 'Pkm was successfully updated.' }
        format.json { render :show, status: :ok, location: @pkm }
      else
        format.html { render :edit }
        format.json { render json: @pkm.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /pkms/1
  # DELETE /pkms/1.json
  def destroy
    @pkm.destroy
    respond_to do |format|
      format.html { redirect_to pkms_url, notice: 'Pkm was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private
  # Use callbacks to share common setup or constraints between actions.
  def set_pkm
    @pkm = Pkm.find(params[:id])
  end

  # Never trust parameters from the scary internet, only allow the white list through.
  def pkm_params
    params.require(:pkm).permit(:comp_id, :name, :region_id)
  end

end

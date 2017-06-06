class PtsController < ApplicationController
  before_action :set_pt, only: [:show, :edit, :update, :destroy]

  def autocomplete
    render json: Autocomplete::Pts.new(view_context)
  end

  # GET /pts
  # GET /pts.json
  def index
    @pts = Pt.all
  end

  # GET /pts/1
  # GET /pts/1.json
  def show
  end

  # GET /pts/new
  def new
    @pt = Pt.new
  end

  # GET /pts/1/edit
  def edit
  end

  # POST /pts
  # POST /pts.json
  def create
    @pt = Pt.new(pt_params)

    respond_to do |format|
      if @pt.save
        format.html { redirect_to @pt, notice: 'Pt was successfully created.' }
        format.json { render :show, status: :created, location: @pt }
      else
        format.html { render :new }
        format.json { render json: @pt.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /pts/1
  # PATCH/PUT /pts/1.json
  def update
    respond_to do |format|
      if @pt.update(pt_params)
        format.html { redirect_to @pt, notice: 'Pt was successfully updated.' }
        format.json { render :show, status: :ok, location: @pt }
      else
        format.html { render :edit }
        format.json { render json: @pt.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /pts/1
  # DELETE /pts/1.json
  def destroy
    @pt.destroy
    respond_to do |format|
      format.html { redirect_to pts_url, notice: 'Pt was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_pt
      @pt = Pt.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def pt_params
      params.require(:pt).permit(:ci_pt, :tipo)
    end
end

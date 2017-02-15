class AddImageToFotos < ActiveRecord::Migration[5.0]
  def change
    add_column :fotos, :image, :string
  end
end

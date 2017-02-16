class AddTipoToArmarios < ActiveRecord::Migration[5.0]
  def change
    add_column :armarios, :tipo, :string
  end
end

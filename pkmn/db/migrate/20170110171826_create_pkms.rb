class CreatePkms < ActiveRecord::Migration[5.0]
  def change
    create_table :pkms do |t|
      t.string :comp_id
      t.string :name
      t.belongs_to :region, index: true
      t.timestamps
    end
  end
end

class CreateTorneioEquipamentoTorneioJogadoresJoinTable < ActiveRecord::Migration[5.1]
  def change
    create_join_table :torneio_equipamentos, :torneio_jogadors
  end
end

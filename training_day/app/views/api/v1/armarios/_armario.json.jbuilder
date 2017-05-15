json.extract! armario, :id, :ci_armario, :created_at, :updated_at
json.url armario_url(armario, format: :json)
json.fotos armario.fotos
